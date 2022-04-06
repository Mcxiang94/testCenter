package com.CcDev.mapper;

import com.CcDev.common.CasesEditVO;
import com.CcDev.common.CasesListVO;
import com.CcDev.pojo.Cases;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
public interface CasesMapper extends BaseMapper<Cases> {
    /**
     * 通过suiteId查询所有的案例();
     *
     * @param suiteId 套件id;
     * @return
     */
    @Select("select *  from cases where suite_id=#{suiteId}")
    List<Cases> findCases(Integer suiteId);

    /**
     * 查询项目下所有案例/拼接接口信息();
     *
     * @param projectId 项目id;
     * @return
     */
    @Select("SELECT DISTINCT t1.*,t6.id apiId,t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id=t2.id JOIN project t3 ON t2.project_id=t3.id JOIN case_param_value t4 ON t1.id=t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id=t5.id JOIN api t6 ON t5.api_id=t6.id WHERE t3.id = #{projectId};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "testReport", column = "id", one = @One(select = "com.CcDev.mapper.TestReportMapper.findByCaseId"))
    })
    List<CasesListVO> showCaseUnderProject(Integer projectId);

    /**
     * 查询suite下的案例/拼接接口信息();
     *
     * @param suiteId 套件id;
     * @return
     */
    @Select("SELECT DISTINCT t1.*,t6.id apiId,t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id=t2.id JOIN case_param_value t4 ON t1.id=t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id=t5.id JOIN api t6 ON t5.api_id=t6.id WHERE t1.suite_id = #{suiteId};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "testReport", column = "id", one = @One(select = "com.CcDev.mapper.TestReportMapper.findByCaseId"))
    })
    List<CasesListVO> showCaseUnderSuiteId(Integer suiteId);

    /**
     * 查询指定case的信息-携带参数CaseEditVO对象();
     *
     * @param caseId 案例id;
     * @return
     */
    @Select("SELECT DISTINCT t1.*,t6.id apiId,t6.url,t6.method FROM cases t1 JOIN case_param_value t2 ON t2.case_id=t1.id JOIN api_request_param t3 ON t2.api_request_param_id=t3.id JOIN api t6 ON t3.api_id=t6.id WHERE t1.id=#{caseId};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requestParams", column = "id", many = @Many(select = "com.CcDev.mapper.ApiRequestParamMapper.findCaseByCaseId")),
            @Result(property = "testRules", column = "id", many = @Many(select = "com.CcDev.mapper.TestRuleMapper.findByCase"))
    })
    CasesEditVO findCaseEditVO(Integer caseId);
}
