package com.CcDev.mapper;

import com.CcDev.common.CasesEditVO;
import com.CcDev.common.ReportVO;
import com.CcDev.pojo.TestReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
public interface TestReportMapper extends BaseMapper<TestReport> {

    @Select("SELECT DISTINCT t1.*,t6.id apiId,t6.method,t6.url url,t3.host FROM cases t1 JOIN suite t2 ON t1.suite_id=t2.id JOIN project t3 ON t2.project_id=t3.id JOIN case_param_value t4 ON t1.id=t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id=t5.id JOIN api t6 ON t5.api_id=t6.id WHERE t2.id = #{suiteId};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requestParams", column = "id", many = @Many(select = "com.CcDev.mapper.ApiRequestParamMapper.findCaseByCaseId")),
            @Result(property = "testRules", column = "id", many = @Many(select = "com.CcDev.mapper.TestRuleMapper.findByCase"))
    })
    List<CasesEditVO> findCaseEditVoUnderSuite(Integer suiteId);

    @Delete("delete from test_report where case_id in (select case_id from suite where id = #{suiteId})")
    void deleteReport(Integer suiteId);

    @Select("select * from test_report where case_id = #{caseId}")
    TestReport findByCaseId(Integer caseId);

    @Select("SELECT * FROM suite  WHERE id = #{suiteId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "caseList", column = "id", many = @Many(select = "com.CcDev.mapper.CasesMapper.showCaseUnderSuiteId")),
    })
    ReportVO getReport(Integer suiteId);
}
