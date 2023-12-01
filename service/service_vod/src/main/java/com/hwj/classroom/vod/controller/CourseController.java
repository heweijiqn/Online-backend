package com.hwj.classroom.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.classroom.model.vod.Course;
import com.hwj.classroom.vo.vod.CourseFormVo;
import com.hwj.classroom.vo.vod.CoursePublishVo;
import com.hwj.classroom.vo.vod.CourseQueryVo;
import com.hwj.classroom.vod.service.CourseService;
import com.hwj.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */

@RestController
@RequestMapping(value="/admin/vod/course")
@Api(tags = "课程管理")
public class CourseController {

    @Autowired
    private CourseService courseService;

    //添加课程基本信息
    @ApiOperation("添加课程基本信息")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    //点播课程列表
    @ApiOperation("点播课程列表")
    @GetMapping("{page}/{limit}")
    public Result courseList(@PathVariable Long page,
                             @PathVariable Long limit,
                             CourseQueryVo courseQueryVo) {
        Page<Course> pageParam = new Page<>(page,limit);
        Map<String,Object> map = courseService.findPageCourse(pageParam,courseQueryVo);
        return Result.ok(map);
    }

    //根据id获取课程信息
    @GetMapping("get/{id}")
    @ApiOperation("根据id获取课程信息")
    public Result get(@PathVariable Long id) {
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);
    }

    //修改课程信息
    @PostMapping("update")
    @ApiOperation("修改课程信息")
    public Result update(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseId(courseFormVo);
        //课程id
        return Result.ok(courseFormVo.getId());
    }

    //根据课程id查询发布课程信息
    @ApiOperation("id查询发布课程信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Long id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    //课程最终发布
    @ApiOperation("课程最终发布")
    @PutMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable Long id) {
        courseService.publishCourse(id);
        return Result.ok(null);
    }

    //删除课程
    @DeleteMapping("remove/{id}")
    @ApiOperation("删除课程")
    public Result remove(@PathVariable Long id) {
        courseService.removeCourseId(id);
        return Result.ok(null);
    }

    //查询所有课程
    @GetMapping("findAll")
    @ApiOperation("全部课程")
    public Result findAll() {
        List<Course> list = courseService.findlist();
        return Result.ok(list);
    }
}


