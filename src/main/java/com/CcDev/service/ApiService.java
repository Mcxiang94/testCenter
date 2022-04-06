package com.CcDev.service;

import com.CcDev.common.ApiListVO;
import com.CcDev.common.ApiRunResult;
import com.CcDev.common.ApiVO;
import com.CcDev.common.Result;
import com.CcDev.pojo.Api;
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
public interface ApiService extends IService<Api> {
    /**
     *
     * @param projectId
     * @return
     */
    public List<ApiListVO>  showApiListByProject(Integer projectId);

    // 查询指定分类抽象方法
    public List<ApiListVO> showApiListByClassificationId(Integer classificationId);

    // 查询指定api信息详情
    public ApiVO findApiViewVO(Integer apiId);

    public Result edit(ApiVO apiVo);

    public ApiRunResult run(ApiVO apiVO);
}
