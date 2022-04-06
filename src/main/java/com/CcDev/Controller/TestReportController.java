package com.CcDev.Controller;


import com.CcDev.common.ReportVO;
import com.CcDev.common.Result;
import com.CcDev.pojo.TestReport;
import com.CcDev.service.TestReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/testReport")
public class TestReportController {

    @Resource
    TestReportService testReportService;

    @PostMapping("/run")
    @ApiOperation(value="执行测试套件",httpMethod="POST")
    public Result run (Integer suiteId) {
        List<TestReport> reportList = testReportService.run(suiteId);
        return new Result("1", reportList, "测试执行成功");
    }


    @PostMapping("/findCaseRunResult")
    @ApiOperation(value="获取单个案例测试结果",httpMethod="POST")
    public Result findCaseRunResult(Integer caseId) {
        TestReport testReport = testReportService.findByCaseId(caseId);
        return new Result("1",testReport,"测试报告");
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取套件下的测试报告",httpMethod = "GET")
    public Result get(Integer suiteId) {
        ReportVO report = testReportService.getReport(suiteId);
        return new Result("1", report);
    }
}
