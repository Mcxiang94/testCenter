package com.CcDev.service.impl;

import com.CcDev.common.ApiVO;
import com.CcDev.common.CasesEditVO;
import com.CcDev.common.CasesListVO;
import com.CcDev.common.Result;
import com.CcDev.pojo.ApiRequestParam;
import com.CcDev.pojo.CaseParamValue;
import com.CcDev.pojo.Cases;
import com.CcDev.mapper.CasesMapper;
import com.CcDev.service.CaseParamValueService;
import com.CcDev.service.CasesService;
import com.CcDev.service.TestRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class CasesServiceImpl extends ServiceImpl<CasesMapper, Cases> implements CasesService {

    @Resource
    CaseParamValueService caseParamValueService;

    @Resource
    CasesMapper casesMapper;

    @Resource
    TestRuleService testRuleService;

    /**
     * 添加测试案例的方法;
     */
    public void add(Cases caseVo, ApiVO apiRunVO) {
        // 添加到cases
        this.save(caseVo);
        // 添加到case_param_value
        List<ApiRequestParam> requestParams = apiRunVO.getRequestParams();

        List<CaseParamValue> caseParamValues = new ArrayList<CaseParamValue>();
        for (ApiRequestParam apiRequestParam : requestParams) {
            CaseParamValue caseParamValue = new CaseParamValue();
            caseParamValue.setCaseId(caseVo.getId());//插入后主键会自动保存到对象中，可以直接进行获取
            caseParamValue.setApiRequestParamId(apiRequestParam.getId());
            caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
            caseParamValues.add(caseParamValue);
        }
        caseParamValueService.saveBatch(caseParamValues);
    }

    /**
     * 查询项目下所有 case信息/拼接api信息的方法;
     */
    @Override
    public List<CasesListVO> showCaseUnderProject(Integer projectId) {
        return casesMapper.showCaseUnderProject(projectId);
    }

    /**
     * 查询套件下所有测试案例/拼接api信息的方法;
     */
    @Override
    public List<CasesListVO> showCaseUnderSuiteId(Integer suiteId) {
        return casesMapper.showCaseUnderSuiteId(suiteId);
    }

    /**
     * 通过caseId查询对应案例信息;
     */
    @Override
    public CasesEditVO findCaseEditVO(Integer caseId) {
        return casesMapper.findCaseEditVO(caseId);
    }

    /**
     * 更新案例信息;
     */
    @Override
    public void updateCases(CasesEditVO caseEditVO) {
        updateById(caseEditVO);
        List<ApiRequestParam> requestParams = caseEditVO.getRequestParams();
        List<CaseParamValue> list = new ArrayList<CaseParamValue>();
        for (ApiRequestParam apiRequestParam : requestParams) {
            CaseParamValue caseParamValue = new CaseParamValue();
            caseParamValue.setId(apiRequestParam.getValueId());
            caseParamValue.setApiRequestParamId(apiRequestParam.getId());
            caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
            caseParamValue.setCaseId(caseEditVO.getId());

            list.add(caseParamValue);
        }
        caseParamValueService.updateBatchById(list);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("case_id", caseEditVO.getId());
        testRuleService.remove(queryWrapper);
        testRuleService.saveBatch(caseEditVO.getTestRules());
    }

}
