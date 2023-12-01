/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/11/2 17:29
 */


package com.hwj.classroom.vod.service.impl;

import com.hwj.classroom.vod.service.PictureService;
import com.hwj.result.Result;
import com.hwj.classroom.until.QiniuUtils;
import com.hwj.classroom.until.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class PictureServiceImpl implements PictureService {


    @Autowired
    private QiniuUtils qiniuUtils;
    @Override
    public Result<String> upload(MultipartFile file) {

        if (file.isEmpty()) {
            return Result.fail("文件不能为空");
        }
        String fileName = StringUtils.getRandomImgName(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            FileInputStream uploadFile = (FileInputStream) file.getInputStream();
            String path = qiniuUtils.upload(uploadFile, fileName);
            return Result.ok(path);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("上传失败");
        }
    }
}
