package com.hwj.classroom.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.live.LiveCourseGoods;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
public interface LiveCourseGoodsService extends IService<LiveCourseGoods> {

    List<LiveCourseGoods> getGoodsListCourseId(Long id);
}
