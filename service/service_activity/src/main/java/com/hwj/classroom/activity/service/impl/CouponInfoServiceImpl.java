package com.hwj.classroom.activity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.classroom.activity.mapper.CouponInfoMapper;
import com.hwj.classroom.activity.service.CouponInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.activity.service.CouponUseService;
import com.hwj.classroom.client.user.UserInfoFeignClient;
import com.hwj.classroom.model.activity.CouponInfo;
import com.hwj.classroom.model.activity.CouponUse;
import com.hwj.classroom.model.user.UserInfo;
import com.hwj.classroom.vo.activity.CouponUseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-05
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {
    @Autowired
    private CouponUseService couponUseService;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;
    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {

        //获取条件
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();
        //封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(couponId)) {
            wrapper.eq("coupon_id",couponId);
        }
        if(!StringUtils.isEmpty(couponStatus)) {
            wrapper.eq("coupon_status",couponStatus);
        }
        if(!StringUtils.isEmpty(getTimeBegin)) {
            wrapper.ge("get_time",getTimeBegin);
        }
        if(!StringUtils.isEmpty(getTimeEnd)) {
            wrapper.le("get_time",getTimeEnd);
        }
        //调用方法查询
        IPage<CouponUse> page = couponUseService.page(pageParam, wrapper);
        page.getRecords().forEach(couponUse -> {
            //获取用户信息
            this.userInfoFe(couponUse);
        });
        return page;
    }

    @Override
    public void updateCouponInfoUseStatus(Long couponUseId, Long orderId) {
        CouponUse couponUse = new CouponUse();
        couponUse.setId(couponUseId);
        couponUse.setOrderId(orderId);
        couponUse.setCouponStatus("1");
        couponUse.setUsingTime(new Date());
        couponUseService.updateById(couponUse);
    }

    private void userInfoFe(CouponUse couponUse) {
        Long userId = couponUse.getUserId();
        if (!StringUtils.isEmpty(userId)){
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if (!StringUtils.isEmpty(userInfo)){
                couponUse.getParam().put("nickName",userInfo.getNickName());
                couponUse.getParam().put("phone",userInfo.getPhone());
            }
        }
    }
}
