package com.CcDev.common;

import lombok.Data;

/**
 * @author Cui
 * @date 2022/3/2 - 16:05
 * @email yuxiangcui6@gmail.com
 */
@Data
public class ApiRunResult {

    private String statusCode;//响应状态码
    private String headers;//响应头部信息
    private String body;//响应体

}
