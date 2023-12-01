package com.hwj.classroom.vod.service;

import com.hwj.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    Result<String> upload(MultipartFile file);
}
