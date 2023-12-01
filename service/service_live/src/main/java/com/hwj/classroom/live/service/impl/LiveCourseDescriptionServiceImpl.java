package com.hwj.classroom.live.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hwj.classroom.live.mapper.LiveCourseDescriptionMapper;
import com.hwj.classroom.live.service.LiveCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.model.live.LiveCourseDescription;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
@Service
public class LiveCourseDescriptionServiceImpl extends ServiceImpl<LiveCourseDescriptionMapper, LiveCourseDescription> implements LiveCourseDescriptionService {

    @Override
    public LiveCourseDescription getLiveCourseById(Long id) {
        LambdaQueryWrapper<LiveCourseDescription> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseDescription::getLiveCourseId,id);  //LambdaQueryWrapper 的使用 ::后面是方法名
        LiveCourseDescription liveCourseDescription = baseMapper.selectOne(wrapper);
        return liveCourseDescription;
    }
}
