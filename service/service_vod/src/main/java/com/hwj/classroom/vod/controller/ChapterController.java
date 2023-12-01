package com.hwj.classroom.vod.controller;


import com.hwj.classroom.model.vod.Chapter;
import com.hwj.classroom.vo.vod.ChapterVo;
import com.hwj.classroom.vod.service.ChapterService;
import com.hwj.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/admin/vod/chapter")
@Api(tags = "课程章节管理")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //1 大纲列表（章节和小节列表）
    @ApiOperation("大纲列表")
    @GetMapping("getNestedTreeList/{courseId}")
    public Result getTreeList(@PathVariable Long courseId) {
        List<ChapterVo> list = chapterService.getTreeList(courseId);
        return Result.ok(list);
    }

    //2 添加章节
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok(null);
    }

    //3 修改-根据id查询
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    //4 修改-最终实现
    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok(null);
    }

    //5 删除章节
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok(null);
    }
}

