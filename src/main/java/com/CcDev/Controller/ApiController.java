package com.CcDev.Controller;


import com.CcDev.common.ApiListVO;
import com.CcDev.common.ApiRunResult;
import com.CcDev.common.ApiVO;
import com.CcDev.common.Result;
import com.CcDev.pojo.User;
import com.CcDev.pojo.Api;
import com.CcDev.service.ApiRequestParamService;
import com.CcDev.service.ApiService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/api")
@io.swagger.annotations.Api("接口模块")
@CrossOrigin
public class ApiController {

    @Resource
    ApiService apiService;

    @Resource
    ApiRequestParamService apiRequestParamService;

    /**
     * 通过项目id查询接口();
     *
     * @param projectId 项目ID;
     * @return Result;
     */
    @GetMapping("/showApiUnderProject")
    @ApiOperation(value = "查询项目下所有接口信息", httpMethod = "GET")
    public Result showApiListByProject(Integer projectId) {
        List<ApiListVO> apiList = apiService.showApiListByProject(projectId);
        return new Result("1", apiList);
    }

    /**
     * 通过分类id查询接口();
     *
     * @param apiClassificationId 接口分类ID;
     * @return Result;
     */
    @GetMapping("/showApiUnderApiClassification")
    @ApiOperation(value = "查询分类下所有接口信息", httpMethod = "GET")
    public Result showApiListByClassificationId(Integer apiClassificationId) {
        List<ApiListVO> apiList = apiService.showApiListByClassificationId(apiClassificationId);
        return new Result("1", apiList);

    }

    /**
     * 新增接口的api();
     *
     * @param api 前端返回的接口对象;
     * @return Result;
     */
    @PostMapping("/addApi")
    @ApiOperation(value = "新增接口", httpMethod = "POST")
    public Result addApi(Api api) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        api.setCreateUser(user.getId());
        apiService.save(api);
        return new Result("1", "新增成功");
    }

    /**
     * 查询指定接口详情信息();
     *
     * @param apiId 接口id;
     * @return Result;
     */
    @GetMapping("/toApiView")
    @ApiOperation(value = "查询指定接口详情信息", httpMethod = "GET")
    public Result findApi(Integer apiId) {
        ApiVO apiVO = apiService.findApiViewVO(apiId);
        return new Result("1", apiVO);
    }

    /**
     * 更新保存接口信息();
     *
     * @param apiEdit 需要修改api;
     * @return Result;
     */
    @PostMapping("/edit")
    @ApiOperation(value = "更新接口", httpMethod = "POST")
    public Result edit(ApiVO apiEdit) {
        // 根据apiId更新api
        apiService.updateById(apiEdit);
        QueryWrapper queryWrapper = new QueryWrapper();
        // 删除原来的apiRequestParam
        queryWrapper.eq("api_id", apiEdit.getId());
        apiRequestParamService.remove(queryWrapper);
        // 插入新的apiRequestParam
        apiEdit.getRequestParams().addAll(apiEdit.getQueryParams());
        apiEdit.getRequestParams().addAll(apiEdit.getHeaderParams());
        apiEdit.getRequestParams().addAll(apiEdit.getBodyParams());
        apiEdit.getRequestParams().addAll(apiEdit.getBodyRawParams());
        apiRequestParamService.saveBatch(apiEdit.getRequestParams());
        return new Result("1", "更新接口成功");
    }

    /**
     * 远程调用，运行接口();
     *
     * @param apiRunVO 接口信息对象;
     * @return Result;
     */
    @RequestMapping("/run")
    public Result run(ApiVO apiRunVO) {
        ApiRunResult apiRunResult = apiService.run(apiRunVO);
        return new Result("1", apiRunResult);
    }
}
