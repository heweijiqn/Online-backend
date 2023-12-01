package com.hwj.classroom.live.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.live.LiveCourse;
import com.hwj.classroom.vo.live.LiveCourseConfigVo;
import com.hwj.classroom.vo.live.LiveCourseFormVo;
import com.hwj.classroom.vo.live.LiveCourseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 直播课程表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
public interface LiveCourseService extends IService<LiveCourse> {

    IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam);

    void saveLive(LiveCourseFormVo liveCourseFormVo);

    void removeLive(Long id);

    LiveCourseFormVo getLiveCourseVo(Long id);

    void updateLiveById(LiveCourseFormVo liveCourseFormVo);

    LiveCourseConfigVo getCourseConfig(Long id);

    void updateConfig(LiveCourseConfigVo liveCourseConfigVo);

    List<LiveCourseVo> getLatelyList();

    JSONObject getAccessToken(Long id, Long userId);

    Map<String, Object> getInfoById(Long courseId);
}
