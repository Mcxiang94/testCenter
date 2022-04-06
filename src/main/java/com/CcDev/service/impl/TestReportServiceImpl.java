package com.CcDev.service.impl;

import com.CcDev.common.CasesEditVO;
import com.CcDev.common.ReportVO;
import com.CcDev.pojo.ApiRequestParam;
import com.CcDev.pojo.TestReport;
import com.CcDev.mapper.TestReportMapper;
import com.CcDev.pojo.TestRule;
import com.CcDev.pojo.User;
import com.CcDev.service.TestReportService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@Service
public class TestReportServiceImpl extends ServiceImpl<TestReportMapper, TestReport> implements TestReportService {

    @Resource
    TestReportMapper testReportMapper;

    /**
     * @param suiteId 套件Id
     */
    @Override
    public List<TestReport> run(Integer suiteId) {
        List<TestReport> reportList = new ArrayList<TestReport>();
        List<CasesEditVO> list = testReportMapper.findCaseEditVoUnderSuite(suiteId);
        for (CasesEditVO casesEditVO : list) {
            TestReport testReport = runAndGetReport(casesEditVO);
            reportList.add(testReport);
        }
        System.out.println(reportList);
        testReportMapper.deleteReport(suiteId);
        this.saveBatch(reportList);
        return reportList;
    }

    /**
     * 获取案例最新的测试结果
     */
    @Override
    public TestReport findByCaseId(Integer caseId) {
        return testReportMapper.findByCaseId(caseId);
    }

    @Override
    public ReportVO getReport(Integer suiteId) {
        ReportVO report = testReportMapper.getReport(suiteId);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        report.setUsername(user.getUsername());
        report.setCreateReportTime(new Date());
        return report;
    }

    /**
     * 运行单个案例的方法();
     *
     * @param caseEditVO;
     * @return testReport
     */
    TestReport runAndGetReport(CasesEditVO caseEditVO) {
        TestReport testReport = new TestReport();
        RestTemplate restTemplate = new RestTemplate();
        String url = caseEditVO.getHost() + caseEditVO.getUrl();
        String method = caseEditVO.getMethod();
        List<ApiRequestParam> paramList = caseEditVO.getRequestParams();
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        LinkedMultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        String paramStr = "?";
        String jsonParam = "";
        for (ApiRequestParam apiRequestParam : paramList) {
            if (apiRequestParam.getType() == 3) {
                // 类型3--请求头
                headers.add(apiRequestParam.getName(), apiRequestParam.getValue());
            } else if (apiRequestParam.getType() == 1) {
                // 类型1--get请求
                paramStr += apiRequestParam.getName() + "=" + apiRequestParam.getValue() + "&";
            } else if (apiRequestParam.getType() == 2) {
                // 类型2
                bodyParams.add(apiRequestParam.getName(), apiRequestParam.getValue());
            } else if (apiRequestParam.getType() == 4) {
                // 类型4:直接使用json字符串即可，不需要使用MultiValueMap格式传入
                jsonParam = apiRequestParam.getValue();
            }
        }

        if (!"?".equals(paramStr)) {
            paramStr = paramStr.substring(0, paramStr.lastIndexOf("&"));
        }

        HttpEntity httpEntity = null;
        ResponseEntity response = null;
        try {
            if ("get".equalsIgnoreCase(method)) {
                httpEntity = new HttpEntity(headers);
                response = restTemplate.exchange(url + paramStr, HttpMethod.GET, httpEntity, String.class);
            } else if ("post".equalsIgnoreCase(method)) {
                if ("".equals(jsonParam)) {
                    httpEntity = new HttpEntity(bodyParams, headers);
                    testReport.setRequestBody(JSON.toJSONString(bodyParams));
                } else {
                    httpEntity = new HttpEntity(jsonParam, headers);
                }
                response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            }
            testReport.setCaseId(caseEditVO.getId());
            testReport.setRequestUrl(url);
            testReport.setRequestHeaders(JSON.toJSONString(headers));
            testReport.setResponseHeaders(JSON.toJSONString(response.getHeaders()));
            testReport.setResponseBody(response.getBody().toString());
            testReport.setPassFlag(assertByTestRule(response.getBody().toString(), caseEditVO.getTestRules()));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return testReport;
    }


    /**
     * 断言的方法后续再优化();
     *
     * @param responseBody;
     * @param testRules;
     * @return 断言结果
     */
    String assertByTestRule(String responseBody, List<TestRule> testRules) {
        Boolean flag = true;
        for (TestRule testRule : testRules) {
            Object value = JSONPath.read(responseBody, testRule.getExpression());
            String actual = (String) value;
            String op = testRule.getOperator();
            if (op.equals("=")) {
                if (!actual.equals(testRule.getExpected())) {
                    flag = false;
                }
            } else {
                if (!actual.contains(testRule.getExpected())) {
                    flag = false;
                }
            }
        }
        if (!flag) {
            return "不通过";
        }
        return "通过";
    }


}
