package com.CcDev.TestProjectController;


import com.CcDev.common.Result;
import com.CcDev.pojo.User;
import com.CcDev.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/user")
@Api("用户模块")
//@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册方法();
     * 调用业务层方法，插入数据到DB，统一处理异常;
     *
     * @param user 请求带来的用户对象;
     * @return Result;
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册方法", httpMethod = "POST")
    public Result register(User user) {
        userService.save(user);
        Result result = new Result("1", "注册成功");
        return result;
    }

    /**
     * 注册用户时,验重的方法();
     * 调用业务层方法，查询DB非主键列，统一处理异常;
     * queryWrapper --> 封装查询条件的对象;
     *
     * @param username 用户名;
     * @return Result;
     */
    @GetMapping("/find")
    @ApiOperation(value = "账号验重方法", httpMethod = "GET")
    public Result find(String username) {
        Result result = null;

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            result = new Result("1", "账号不存在");
        } else {
            result = new Result("0", "账号已存在");
        }
        return result;
    }


    /**
     * 登录方法();
     * 验证账户安全的部分交给shiro去完成;
     * token --> 通过(用户名+密码)的方法得到的token;
     * sessionId --> 得到返回的session给前端
     *
     * @param user 请求带来的用户对象;
     * @return Result;
     */
    @PostMapping("/login")
    @ApiOperation(value = "注册方法", httpMethod = "POST")
    public Result login(User user) {
        Result result = null;

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
            User loginUser = (User) subject.getPrincipal();
            result = new Result("1", loginUser.getId(), sessionId);
        } catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException) {
                result = new Result("0", "用户名错误");
            } else {
                result = new Result("0", "密码错误");
            }
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 登出方法();
     *
     * @return Result；
     */
    @GetMapping("/logout")
    @ApiOperation(value = "登出方法", httpMethod = "GET")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return new Result("1", "账号未登录");
    }

    /**
     * 未授权方法();
     * 发现页面并没有登录，但是却对页面进行操作时;
     *
     * @return Result;
     */
    @GetMapping("/unauth")
    @ApiOperation(value = "未授权方法", httpMethod = "GET")
    public Result unauth() {
        return new Result("1", "账号未登录");
    }
}
