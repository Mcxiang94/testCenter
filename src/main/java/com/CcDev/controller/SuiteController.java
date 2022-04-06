package com.CcDev.controller;


import com.CcDev.common.Result;
import com.CcDev.pojo.Suite;
import com.CcDev.service.SuiteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/suite")
public class SuiteController {

    @Resource
    SuiteService suiteService;

    @GetMapping("listAll")
    @ApiOperation(value="查询项目的测试集合",httpMethod="GET")
    public Result findAll (Integer projectId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("project_id", projectId);
        List<Suite> list = suiteService.list(queryWrapper);
        return new Result("1", list);
    }
}
