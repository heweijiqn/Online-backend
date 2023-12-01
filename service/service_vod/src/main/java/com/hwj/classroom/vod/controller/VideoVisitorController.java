package com.hwj.classroom.vod.controller;


import com.hwj.classroom.vod.service.VideoVisitorService;
import com.hwj.result.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-11-03
 */
@RestController
@RequestMapping(value="/admin/vod/videoVisitor")
@Api(tags = "视频来访者记录表")
public class VideoVisitorController {

    @Autowired
    private VideoVisitorService videoVisitorService;

    //课程统计的接口
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result findCount(@PathVariable Long courseId,
                            @PathVariable String startDate,
                            @PathVariable String endDate) {
        Map<String,Object> map =
                videoVisitorService.findCount(courseId,startDate,endDate);
        return Result.ok(map);
    }

}

