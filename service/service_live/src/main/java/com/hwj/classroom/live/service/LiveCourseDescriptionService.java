package com.hwj.classroom.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.live.LiveCourseDescription;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
public interface LiveCourseDescriptionService extends IService<LiveCourseDescription> {

    LiveCourseDescription getLiveCourseById(Long id);
}
