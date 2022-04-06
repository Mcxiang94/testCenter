package com.CcDev.service;

import com.CcDev.common.ReportVO;
import com.CcDev.pojo.TestReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
public interface TestReportService extends IService<TestReport> {
    public List<TestReport> run(Integer suiteId);

    public TestReport findByCaseId(Integer caseId);

    public ReportVO getReport(Integer suiteId);
}
