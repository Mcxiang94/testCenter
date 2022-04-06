package com.CcDev.common;

import com.CcDev.pojo.TestReport;
import lombok.Data;

/**
 * @author Cui
 * @date 2022/3/7 - 10:52
 * @email yuxiangcui6@gmail.com
 */
@Data
public class CasesListVO {
    // caseId
    private String id;

    // caseName
    private String name;

    // 接口id
    private String apiId;

    // 接口地址
    private String apiUrl;

    // 测试报告
    private TestReport testReport;

}
