package com.hwj.classroom.vod.service;

import com.hwj.classroom.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-03
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
