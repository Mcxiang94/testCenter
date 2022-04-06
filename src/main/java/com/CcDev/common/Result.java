package com.CcDev.common;

import lombok.Data;

/**
 * @author Cui
 * @date 2022/2/17 - 20:41
 * @email yuxiangcui6@gmail.com
 */
@Data
public class Result {
    private String status;
    private Object data;
    private String message;

    public Result(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
