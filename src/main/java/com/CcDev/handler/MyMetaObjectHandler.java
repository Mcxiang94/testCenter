package com.CcDev.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Cui
 * @date 2022/2/19 - 20:14
 * @email yuxiangcui6@gmail.com
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("regtime", new Date(), metaObject); //用户表注册时间
        this.setFieldValByName("createTime", new Date(), metaObject); //项目表、接口分类表 创建时间
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
