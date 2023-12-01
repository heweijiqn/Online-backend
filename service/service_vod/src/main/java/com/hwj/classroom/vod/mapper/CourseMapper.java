package com.hwj.classroom.vod.mapper;

import com.hwj.classroom.model.vod.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwj.classroom.vo.vod.CoursePublishVo;
import com.hwj.classroom.vo.vod.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo selectCoursePublishVoById(Long id);

    CourseVo selectCourseVoById(Long courseId);
}
