package com.hwj.classroom.live.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hwj.classroom.live.mapper.LiveCourseGoodsMapper;
import com.hwj.classroom.live.service.LiveCourseGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.model.live.LiveCourseGoods;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
@Service
public class LiveCourseGoodsServiceImpl extends ServiceImpl<LiveCourseGoodsMapper, LiveCourseGoods> implements LiveCourseGoodsService {

    @Override
    public List<LiveCourseGoods> getGoodsListCourseId(Long id) {
        return baseMapper.selectList(new LambdaQueryWrapper<LiveCourseGoods>()
                .eq(LiveCourseGoods::getLiveCourseId, id));
    }
}
