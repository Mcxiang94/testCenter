package com.CcDev.service;

import com.CcDev.pojo.Suite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
public interface SuiteService extends IService<Suite> {
    List<Suite> findSuiteAndRelatedCasesBy(Integer projectId);
}
