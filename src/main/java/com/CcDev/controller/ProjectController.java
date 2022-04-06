package com.CcDev.controller;


import com.CcDev.common.Result;
import com.CcDev.pojo.Project;
import com.CcDev.pojo.User;
import com.CcDev.service.ProjectService;
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
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 查询项目列表();
     *
     * @param userId 通过userid查询;
     * @return
     */
    @GetMapping("toList")
    @ApiOperation(value = "查询项目列表", httpMethod = "GET")
    public Result toList(Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("create_user", userId);
        List list = projectService.list(queryWrapper);
        return new Result("1", list, "项目列表");
    }

    /**
     * 新增项目方法();
     *
     * @param project
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增项目", httpMethod = "POST")
    public Result add(Project project) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        project.setCreateUser(user.getId());
        projectService.save(project);
        return new Result("1", "项目添加成功");
    }

    /**
     * 查询项目信息方法()
     * 使用rest风格访问路径;
     *
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}")
    @ApiOperation(value = "查询项目信息", httpMethod = "GET")
    public Result getProjectById(@PathVariable("projectId") Integer projectId) {
        Project project = projectService.getById(projectId);
        return new Result("1", project, "项目信息");
    }

    /**
     * 更新项目信息();
     *
     * @param projectId
     * @param project
     * @return
     */
    @PostMapping("/{projectId}")
    @ApiOperation(value = "更新项目信息", httpMethod = "POST")
    public Result updateById(@PathVariable("projectId") Integer projectId, Project project) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        project.setCreateUser(user.getId());
        project.setId(projectId);
        projectService.updateById(project);
        return new Result("1", project, "更新成功");
    }


}
