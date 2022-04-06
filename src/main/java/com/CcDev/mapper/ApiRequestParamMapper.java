package com.CcDev.mapper;

import com.CcDev.pojo.ApiRequestParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
public interface ApiRequestParamMapper extends BaseMapper<ApiRequestParam> {

    /**
     * 通过apiId查询api参数();
     *
     * @param apiId;
     * @return
     */
    @Select("select  *  from api_request_param  where api_id = #{apiId}")
    public List<ApiRequestParam> findApiParam(Integer apiId);

    /**
     * 通过caseId查询参数();
     *
     * @param caseId;
     */
    @Select("SELECT DISTINCT t2.*, t1.api_request_param_value value,t1.id valueId FROM case_param_value t1 JOIN api_request_param t2 ON t1.api_request_param_id=t2.id WHERE t1.case_id = #{caseId};")
    public List<ApiRequestParam> findCaseByCaseId(Integer caseId);
}
