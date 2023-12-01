/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/11/7 20:39
 */


package com.hwj.classroom.vod.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.classroom.model.vod.Course;
import com.hwj.classroom.vo.vod.CourseQueryVo;
import com.hwj.classroom.vod.service.CourseService;
import com.hwj.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api/vod/course")
@Api(tags = "微信课程管理")
public class CourseApiController {

    @Autowired
    private CourseService courseService;


    //课程id查询课程信息
    @ApiOperation("根据ID查询课程")
    @GetMapping("inner/getById/{courseId}")
    public Course getById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long courseId){
        Course course = courseService.getById(courseId);
        return course;
    }

    @ApiOperation("根据关键字查询课程")
    @GetMapping("inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(
            @ApiParam(value = "关键字", required = true)
            @PathVariable String keyword){
        QueryWrapper<Course> queryWrapper = new QueryWrapper();
        queryWrapper.like("title", keyword);
        List<Course> list = courseService.list(queryWrapper);
        return list;
    }

    //根据课程分类查询课程列表（分页）
    @ApiOperation("根据课程分类查询课程列表")
    @GetMapping("{subjectParentId}/{page}/{limit}")
    public Result findPageCourse(@ApiParam(value = "课程一级分类ID", required = true)
                                 @PathVariable Long subjectParentId,
                                 @ApiParam(name = "page", value = "当前页码", required = true)
                                 @PathVariable Long page,
                                 @ApiParam(name = "limit", value = "每页记录数", required = true)
                                 @PathVariable Long limit) {
        //封装条件
        CourseQueryVo courseQueryVo = new CourseQueryVo();
        courseQueryVo.setSubjectParentId(subjectParentId);
        //创建page对象
        Page<Course> pageParam = new Page<>(page,limit);
        Map<String,Object> map = courseService.findPage(pageParam,courseQueryVo);
        return Result.ok(map);
    }


    //根据课程id查询课程详情
    @ApiOperation("根据课程id查询课程详情")
    @GetMapping("getInfo/{courseId}")
    public Result getInfo(@PathVariable Long courseId) {
        Map<String,Object> map = courseService.getInfoById(courseId);
        return Result.ok(map);
    }

}
