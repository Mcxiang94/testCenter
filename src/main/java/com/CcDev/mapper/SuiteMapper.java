package com.CcDev.mapper;

import com.CcDev.pojo.Suite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
public interface SuiteMapper extends BaseMapper<Suite> {
    @Select("select *  from suite where project_id=#{projectId}")
    @Results({
            @Result(column = "id", property = "id"), //如果查询的结果列名与实体类名称一致；可以省略com.CcDev.mapper.CasesMapper
            @Result(column = "id", property = "cases", many = @Many(select = "com.CcDev.mapper.CasesMapper.findCases"))
    })
    List<Suite> findSuiteAndRelatedCasesBy(Integer projectId);
}
