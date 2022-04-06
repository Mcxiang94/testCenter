package com.CcDev.TestProjectController;


import com.CcDev.common.ApiClassificationVO;
import com.CcDev.common.Result;
import com.CcDev.pojo.ApiClassification;
import com.CcDev.pojo.Suite;
import com.CcDev.pojo.User;
import com.CcDev.service.ApiClassificationService;
import com.CcDev.service.SuiteService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
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
@RequestMapping("/apiClassification")
@Api("接口分类模块")
public class ApiClassificationController {

    @Resource
    ApiClassificationService apiClassificationService;

    @Resource
    SuiteService suiteService;

    /**
     * 新增接口分类的方法();
     *
     * @param projectId         项目id;
     * @param apiClassification 分类对象;
     * @return Result;
     */
    @PostMapping("/{projectId}")
    @ApiOperation(value = "新增接口分类方法", httpMethod = "POST")
    public Result addApiClassification(@PathVariable("projectId") Integer projectId, ApiClassification apiClassification) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        apiClassification.setProjectId(projectId);
        apiClassification.setCreateUser(user.getId());
        apiClassificationService.save(apiClassification);
        return new Result("1", "新增成功");
    }

    /**
     * 查询接口分类和接口列表方法();
     *
     * @param projectId 项目id;
     * @param tab       查询类型:1-接口列表,2-测试集合;
     * @return Result;
     */
    @GetMapping("/toIndex")
    @ApiOperation(value = "查询接口分类和接口列表方法", httpMethod = "GET")
    public Result getWithApi(Integer projectId, String tab) {
        Result result = null;
        if (tab.equals("1")) {
            //接口列表
            List<ApiClassificationVO> apiClassificationVOlist = apiClassificationService.getWithApi(projectId);
            result = new Result("1", apiClassificationVOlist, "查询接口分类和接口");
        } else if ("2".equals(tab)) {
            // 测试集合
            List<Suite> suites=suiteService.findSuiteAndRelatedCasesBy(projectId);
            result=new Result("1", suites, "查询集合和案例");
        }
        return result;
    }

    /**
     * 删除接口分类的方法();
     *
     * @param apiClassificationId 分类对象;
     * @return Result;
     */
    @GetMapping("/deleteApiClassification")
    @ApiOperation(value = "删除接口分类的方法", httpMethod = "GET")
    public Result deleteApiClassification(Integer apiClassificationId) {
        apiClassificationService.removeById(apiClassificationId);
        return new Result("1", "删除接口分类成功");
    }

    /**
     * 查询分类信息();
     *
     * @param apiClassificationId 分类对象ID;
     * @return Result;
     */
    @GetMapping("/{apiClassificationId}")
    @ApiOperation(value = "查询分类信息", httpMethod = "GET")
    public Result getApiClassificationById(@PathVariable("apiClassificationId") Integer apiClassificationId) {
        ApiClassification apiClassification = apiClassificationService.getById(apiClassificationId);
        return new Result("1", apiClassification);
    }

    /**
     * 更新分类信息();
     *
     * @param apiClassification 分类对象;
     * @return Result;
     */
    @PostMapping("/updateApiClassification/{apiClassificationId}")
    @ApiOperation(value = "更新分类信息", httpMethod = "POST")
    public Result updateApiClassification(ApiClassification apiClassification, @PathVariable("apiClassificationId") Integer apiClassificationId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        apiClassification.setId(apiClassificationId);
        apiClassification.setCreateUser(user.getId());
        apiClassificationService.updateById(apiClassification);
        return new Result("1", "更新接口分类成功");
    }

    /**
     * 查询所有分类信息方法();
     *
     * @param projectId 项目ID;
     * @return Result;
     */
    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有分类信息", httpMethod = "GET")
    public Result findAll(Integer projectId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("project_id", projectId);
        List<ApiClassification> list = apiClassificationService.list(queryWrapper);
        return new Result("1", list);
    }

    /**
     * 调试方法-新增接口分类方法();
     *
     * @param jsonStr 前端传的Json;
     * @return Result;
     */
    @PostMapping("/add2")
    @ApiOperation(value="调试方法-新增分类方法2json格式",httpMethod="POST")
    public Result add2(@RequestBody String jsonStr) {
        ApiClassification apiClassification = JSON.parseObject(jsonStr, ApiClassification.class);
        apiClassificationService.save(apiClassification);
        return new Result("1", "新增成功");
    }
}
