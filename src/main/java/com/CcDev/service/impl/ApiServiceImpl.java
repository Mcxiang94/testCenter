package com.CcDev.service.impl;

import com.CcDev.common.ApiListVO;
import com.CcDev.common.ApiRunResult;
import com.CcDev.common.ApiVO;
import com.CcDev.common.Result;
import com.CcDev.pojo.Api;
import com.CcDev.mapper.ApiMapper;
import com.CcDev.pojo.ApiRequestParam;
import com.CcDev.service.ApiService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
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
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {
    @Resource
    ApiMapper apiMapper;

    /**
     * 业务层调用dao层--查询指定项目下接口列表
     */
    @Override
    public List<ApiListVO> showApiListByProject(Integer projectId) {
        return apiMapper.showApiListByProject(projectId);
    }

    /**
     * 业务层调用dao层 --查询指定分类下接口
     */
    @Override
    public List<ApiListVO> showApiListByClassificationId(Integer classificationId) {
        return apiMapper.showApiListByClassificationId(classificationId);
    }

    /**
     * 业务层调用dao层-查询指定接口信息
     */
    @Override
    public ApiVO findApiViewVO(Integer apiId) {
        return apiMapper.findApiViewVO(apiId);
    }

    @Override
    public Result edit(ApiVO apiVo) {
        return null;
    }

    @Override
    public ApiRunResult run(ApiVO apiRunVO) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiRunVO.getHost() + apiRunVO.getUrl();
        String method = apiRunVO.getMethod();
        List<ApiRequestParam> paramList = apiRunVO.getRequestParams(); // 获取请求体
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

        // 整合参数信息，发送请求得到响应结果
        HttpEntity httpEntity = null;
        ResponseEntity response = null;
        ApiRunResult apiRunResult = new ApiRunResult();
        try {
            if ("get".equalsIgnoreCase(method)) {
                httpEntity = new HttpEntity(headers);
                response = restTemplate.exchange(url + paramStr, HttpMethod.GET, httpEntity, String.class);
            } else if ("post".equalsIgnoreCase(method)) {
                if ("".equals(jsonParam)) {
                    httpEntity = new HttpEntity(bodyParams, headers);
                } else {
                    httpEntity = new HttpEntity(jsonParam, headers);
                }
                response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            }
            apiRunResult.setStatusCode(response.getStatusCodeValue() + "");
            HttpHeaders headersResult = response.getHeaders();
            apiRunResult.setHeaders(JSON.toJSONString(headersResult));
            apiRunResult.setBody(response.getBody().toString());
        } catch (HttpStatusCodeException e) {
            // 如果出现异常获取异常对应信息
            apiRunResult.setStatusCode(e.getRawStatusCode() + "");
            apiRunResult.setHeaders(JSON.toJSONString(e.getResponseHeaders()));
            apiRunResult.setBody(e.getResponseBodyAsString());
        }
        return apiRunResult;
    }
}
