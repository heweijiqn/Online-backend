package com.hwj.classroom.vod.service;

import java.util.Map;

public interface VodService {



    //删除腾讯云视频
    void removeVideo(String fileId);

    String updateVideo();

    Map<String, Object> getPlayAuth(Long courseId, Long videoId);

    //上传视频

}
