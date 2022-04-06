package com.CcDev.service.impl;

import com.CcDev.pojo.Suite;
import com.CcDev.mapper.SuiteMapper;
import com.CcDev.service.SuiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@Service
public class SuiteServiceImpl extends ServiceImpl<SuiteMapper, Suite> implements SuiteService {
    @Resource
    SuiteMapper suiteMapper;

    @Override
    public List<Suite> findSuiteAndRelatedCasesBy(Integer projectId) {
        // TODO Auto-generated method stub
        return suiteMapper.findSuiteAndRelatedCasesBy(projectId);
    }
}
