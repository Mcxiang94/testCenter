package com.CcDev.service.impl;

import com.CcDev.common.ApiClassificationVO;
import com.CcDev.pojo.ApiClassification;
import com.CcDev.mapper.ApiClassificationMapper;
import com.CcDev.service.ApiClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApiClassificationServiceImpl extends ServiceImpl<ApiClassificationMapper, ApiClassification> implements ApiClassificationService {

    @Resource
    ApiClassificationMapper apiClassificationMapper;//dao层对象

    /**
     * 服务层调用dao层操作数据方法
     */
    @Override
    public List<ApiClassificationVO> getWithApi(Integer projectId) {
        return apiClassificationMapper.getWithApi(projectId);
    }
}
