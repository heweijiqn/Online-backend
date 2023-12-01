/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/11/14 20:29
 */


package com.hwj.classroom.live.api;

import com.alibaba.fastjson.JSONObject;
import com.hwj.classroom.live.service.LiveCourseService;
import com.hwj.result.Result;
import com.hwj.utlis.AuthContextHolder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/live/liveCourse")
public class LiveCourseApiController {

    @Autowired
    private LiveCourseService liveCourseService;

    @ApiOperation(value = "获取用户access_token")
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable Long id) {
        JSONObject object = liveCourseService.getAccessToken(id, AuthContextHolder.getUserId());
        return Result.ok(object);
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("getInfo/{courseId}")
    public Result getInfo(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long courseId){
        Map<String, Object> map = liveCourseService.getInfoById(courseId);
        return Result.ok(map);
    }


}
