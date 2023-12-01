package com.hwj.classroom.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.vod.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
public interface VideoService extends IService<Video> {

    void removeVideoById(Long id);

    void removeVideoByCourseId(Long id);
}
