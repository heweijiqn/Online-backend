package com.hwj.classroom.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.live.LiveCourseConfig;

/**
 * <p>
 * 直播课程配置表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
public interface LiveCourseConfigService extends IService<LiveCourseConfig> {

    LiveCourseConfig getCourseConfigCourseId(Long id);
}
