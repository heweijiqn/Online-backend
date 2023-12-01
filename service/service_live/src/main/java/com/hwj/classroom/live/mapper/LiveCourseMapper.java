package com.hwj.classroom.live.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwj.classroom.model.live.LiveCourse;
import com.hwj.classroom.vo.live.LiveCourseVo;

import java.util.List;

/**
 * <p>
 * 直播课程表 Mapper 接口
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
public interface LiveCourseMapper extends BaseMapper<LiveCourse> {

    List<LiveCourseVo> getLatelyList();
}
