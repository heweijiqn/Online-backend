package com.hwj.classroom.live.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hwj.classroom.live.mapper.LiveCourseConfigMapper;
import com.hwj.classroom.live.service.LiveCourseConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.model.live.LiveCourseConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 直播课程配置表 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
@Service
public class LiveCourseConfigServiceImpl extends ServiceImpl<LiveCourseConfigMapper, LiveCourseConfig> implements LiveCourseConfigService {

    @Override
    public LiveCourseConfig getCourseConfigCourseId(Long id) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LiveCourseConfig>().eq(
                LiveCourseConfig::getLiveCourseId,
                id));
    }
}
