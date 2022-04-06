package com.CcDev.common;

import com.CcDev.pojo.TestReport;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Cui
 * @date 2022/3/14 - 14:39
 * @email yuxiangcui6@gmail.com
 */
@Data
public class ReportVO {
    private Integer id;
    private String name;    // 套件名称

    private String username;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createReportTime;

    private int totalCase;
    private int successes;
    private int failures;

    private List<CasesListVO> caseList;

    public int getTotalCase(){
        return caseList.size();
    }

    public int getSuccesses(){
        int count1=0,count2=0;
        for (CasesListVO casesListVO : caseList) {
            TestReport report = casesListVO.getTestReport();
            if (report != null) {
                if (report.getPassFlag().equals("通过")) {
                    count1++;
                } else {
                    count2++;
                }
            }
        }
        this.successes=count1;
        this.failures=count2;
        return successes;
    }

    public int getFailures(){
        return failures;
    }
}
