package com.hwj.classroom.vod.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.vod.Course;
import com.hwj.classroom.vo.vod.CourseFormVo;
import com.hwj.classroom.vo.vod.CoursePublishVo;
import com.hwj.classroom.vo.vod.CourseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
public interface CourseService extends IService<Course> {

    Long saveCourseInfo(CourseFormVo courseFormVo);

    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    CourseFormVo getCourseInfoById(Long id);

    void updateCourseId(CourseFormVo courseFormVo);

    CoursePublishVo getCoursePublishVo(Long id);

    void publishCourse(Long id);

    void removeCourseId(Long id);

    List<Course> findlist();

    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Map<String, Object> getInfoById(Long courseId);
}
