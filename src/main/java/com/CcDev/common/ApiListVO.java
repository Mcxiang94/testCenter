package com.CcDev.common;

import lombok.Data;

/**
 * @author Cui
 * @date 2022/2/23 - 8:44
 * @email yuxiangcui6@gmail.com
 */

@Data
public class ApiListVO {

    //id：接口id
    private  String id;

    //name:接口名称
    private  String name;

    //method 请求方式
    private  String method;

    //url：请求路径
    private  String url;

    //classificationName:所属分类
    private  String classificationName;

    private  String classificationDescription;
}
