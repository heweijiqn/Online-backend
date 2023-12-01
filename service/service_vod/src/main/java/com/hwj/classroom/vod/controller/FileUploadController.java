package com.hwj.classroom.vod.controller;

import com.hwj.classroom.vod.service.PictureService;
import com.hwj.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
public class FileUploadController {

    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {

        return pictureService.upload(file);
    }
}
