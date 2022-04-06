package com.CcDev.common;

import com.CcDev.pojo.ApiRequestParam;
import com.CcDev.pojo.Cases;
import com.CcDev.pojo.TestRule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cui
 * @date 2022/3/7 - 10:53
 * @email yuxiangcui6@gmail.com
 */
@Data
public class CasesEditVO extends Cases {

//    private Integer id;

    // 用例名称
//    private String name;

    private String method;

    private String url;

    private Integer apiId;

    private String host;

    private List<ApiRequestParam> requestParams = new ArrayList<ApiRequestParam>();

    private List<TestRule> testRules = new ArrayList<TestRule>();
}
