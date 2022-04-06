package com.CcDev.mapper;

import com.CcDev.pojo.TestRule;
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
public interface TestRuleMapper extends BaseMapper<TestRule> {
    @Select("SELECT*FROM test_rule WHERE case_id = #{caseId};")
    public List<TestRule> findByCase(Integer caseId);
}
