package com.CcDev.controller;


import com.CcDev.common.ApiVO;
import com.CcDev.common.CasesEditVO;
import com.CcDev.common.CasesListVO;
import com.CcDev.common.Result;
import com.CcDev.pojo.Cases;
import com.CcDev.service.CasesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Cui.chengsheng
 * @since 2022-02-17
 */
@RestController
@RequestMapping("/cases")
public class CasesController {

    @Resource
    CasesService casesService;

    /**
     * 添加测试案例到集合中();
     *
     * @param caseVo;
     * @param apiRunVO;
     * @return Result;
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加测试集", httpMethod = "POST")
    public Result add(Cases caseVo, ApiVO apiRunVO) {
        casesService.add(caseVo, apiRunVO);
        return new Result("1", "添加测试集成功");
    }

    /**
     * 通过项目id查询所有测试案例();
     *
     * @param projectId;
     * @return Result;
     */
    @GetMapping("/findCasesByProjectId")
    @ApiOperation(value = "查询项目下所有测试案例", httpMethod = "GET")
    public Result findCasesByProjectId(Integer projectId) {
        List<CasesListVO> casesListVOs = casesService.showCaseUnderProject(projectId);
        return new Result("1", casesListVOs, "1");
    }

    /**
     * 通过集合id查询集合下测试案例();
     *
     * @param suiteId 集合id;
     * @return Result;
     */
    @GetMapping("/findCasesBySuiteId")
    @ApiOperation(value = "查询集合下测试案例", httpMethod = "GET")
    public Result findCasesBySuiteId(Integer suiteId) {
        List<CasesListVO> casesListVOs = casesService.showCaseUnderSuiteId(suiteId);
        return new Result("1", casesListVOs, "1");
    }

    /**
     * 通过caseId查询案例信息();
     *
     * @param caseId 案例id;
     * @return Result;
     */
    @GetMapping("/toCaseEdit")
    @ApiOperation(value = "查询案例信息", httpMethod = "GET")
    public Result findCasesEditVO(Integer caseId) {
        CasesEditVO casesEditVO = casesService.findCaseEditVO(caseId);
        return new Result("1", casesEditVO);

    }

    /**
     * 更新案例信息();
     *
     * @param casesEditVO 案例封装对象;
     * @return Result;
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新案例信息", httpMethod = "POST")
    public Result updateCases(CasesEditVO casesEditVO) {
        casesService.updateCases(casesEditVO);
        return new Result("1", casesEditVO,"案例更新成功");
    }
}
