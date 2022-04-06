package com.CcDev.service;

import com.CcDev.common.ApiClassificationVO;
import com.CcDev.pojo.ApiClassification;
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
public interface ApiClassificationService extends IService<ApiClassification> {

    /**
     * 查询接口和接口分类的抽象方法();
     * @param projectId  项目id
     * @return
     */
    public List<ApiClassificationVO> getWithApi(Integer projectId);
}
