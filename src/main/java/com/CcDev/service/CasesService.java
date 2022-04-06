package com.CcDev.service;

import com.CcDev.common.ApiVO;
import com.CcDev.common.CasesEditVO;
import com.CcDev.common.CasesListVO;
import com.CcDev.pojo.Cases;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
public interface CasesService extends IService<Cases> {

    /**
     * 添加测试案例的方法
     *
     * @param caseVo
     * @param apiRunVO
     * @return
     */
     void add(Cases caseVo, ApiVO apiRunVO);

    /**
     * 查询项目下所有测试案例/拼接接口信息
     *
     * @param projectId
     * @return
     */
    List<CasesListVO> showCaseUnderProject(Integer projectId);

    /**
     * 查询套件下所有测试案例的方法/拼接接口信息
     *
     * @param suiteId
     * @return
     */
    List<CasesListVO> showCaseUnderSuiteId(Integer suiteId);

    /**
     * 通过caseId查询案例相关信息/接口/参数/项目信息等
     *
     * @param caseId
     * @return
     */
    CasesEditVO findCaseEditVO(Integer caseId);

    /**
     * 更新案例
     *
     * @param caseEditVO
     */
    void updateCases(CasesEditVO caseEditVO);

}
