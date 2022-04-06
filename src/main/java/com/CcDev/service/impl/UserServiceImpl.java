package com.CcDev.service.impl;

import com.CcDev.pojo.User;
import com.CcDev.mapper.UserMapper;
import com.CcDev.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
