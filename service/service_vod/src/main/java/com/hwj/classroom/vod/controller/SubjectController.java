package com.hwj.classroom.vod.controller;


import com.hwj.classroom.model.vod.Subject;
import com.hwj.classroom.vod.service.SubjectService;
import com.hwj.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping(value="/admin/vod/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    //课程分类列表
    //懒加载，每次查询一层数据
    @ApiOperation("课程分类列表")
    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(@PathVariable Long id) {
        List<Subject> list = subjectService.selectSubjectList(id);
        return Result.ok(list);
    }

    @ApiOperation("课程分类导出")
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    //课程分类导入
    @ApiOperation("课程分类导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        subjectService.importData(file);
        return Result.ok(null);
    }

}

