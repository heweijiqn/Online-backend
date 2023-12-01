package com.hwj.classroom.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwj.classroom.client.activity.CouponInfoFeignClient;
import com.hwj.classroom.client.user.CourseFeignClient;
import com.hwj.classroom.client.user.UserInfoFeignClient;
import com.hwj.classroom.model.activity.CouponInfo;
import com.hwj.classroom.model.order.OrderDetail;
import com.hwj.classroom.model.order.OrderInfo;
import com.hwj.classroom.model.user.UserInfo;
import com.hwj.classroom.model.vod.Course;
import com.hwj.classroom.order.mapper.OrderInfoMapper;
import com.hwj.classroom.order.service.OrderDetailService;
import com.hwj.classroom.order.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.vo.order.OrderFormVo;
import com.hwj.classroom.vo.order.OrderInfoQueryVo;
import com.hwj.exception.HwjException;
import com.hwj.utlis.AuthContextHolder;
import com.hwj.utlis.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-04
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    @Autowired
    private CouponInfoFeignClient couponInfoFeignClient;

    @Override
    public Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, // pageParm是分页参数，里面有分页信息，page是当前页，limit是每页显示条数，默认是10条，count是总条数，total是总页数
                                                   OrderInfoQueryVo orderInfoQueryVo) {
        //orderInfoQueryVo获取查询条件
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();

        //判断条件值是否为空，不为空，进行条件封装
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq("order_status",orderStatus);
        }
        if(!StringUtils.isEmpty(userId)) {
            wrapper.eq("user_id",userId);
        }
        if(!StringUtils.isEmpty(outTradeNo)) {
            wrapper.eq("out_trade_no",outTradeNo);
        }
        if(!StringUtils.isEmpty(phone)) {
            wrapper.eq("phone",phone);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time",createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le("create_time",createTimeEnd);
        }
        //调用实现条件分页查询
        Page<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long pageCount = pages.getPages();
        List<OrderInfo> records = pages.getRecords(); //查询结果
        //订单里面包含详情内容，封装详情数据，根据订单id查询详情
        records.stream().forEach(item -> {
            this.getOrderDetail(item);
        });

        //所有需要数据封装map集合，最终返回
        Map<String,Object> map = new HashMap<>();
        map.put("total",totalCount);
        map.put("pageCount",pageCount);
        map.put("records",records);
        return map;
    }

    @Override
    public Long submitOrder(OrderFormVo orderFormVo) {
        //1 获取生成订单条件值
        //1 获取生成订单条件值
        Long courseId = orderFormVo.getCourseId();
        Long couponId = orderFormVo.getCouponId();
        Long userId = AuthContextHolder.getUserId();  //根据用户id查询用户信息
        //2 判断当前用户,针对当前课程是否已经生成订单
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getCourseId,courseId);
        wrapper.eq(OrderDetail::getUserId,userId);
        OrderDetail orderDetailExists = orderDetailService.getOne(wrapper);
        if(orderDetailExists != null) {
            return orderDetailExists.getId();//订单已经存在，直接返回订单id
        }
        //课程id，用户id
        //3 根据课程id查询课程信息
        Course course = courseFeignClient.getById(courseId);
        if(course == null){
            throw new HwjException(20001,"课程不存在");
        }
        //4 根据用户id查询用户信息
        UserInfo userInfo = userInfoFeignClient.getById(userId);
        if(userInfo == null){
            throw new HwjException(20001,"用户不存在");
        }
        //5 根据优惠券id查询优惠券信息
        BigDecimal couponReduce = new BigDecimal(0);
        if(couponId != null) {
            CouponInfo couponInfo = couponInfoFeignClient.getById(couponId);
            couponReduce = couponInfo.getAmount();
        }
        //6 封装订单生成需要数据到对象，完成添加订单
        //6.1 封装数据到OrderInfo对象里面，添加订单基本信息表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setPhone(userInfo.getPhone());
        orderInfo.setProvince(userInfo.getProvince());
        orderInfo.setOriginAmount(course.getPrice());
        orderInfo.setCouponReduce(couponReduce);
        orderInfo.setFinalAmount(orderInfo.getOriginAmount().subtract(orderInfo.getCouponReduce()));
        orderInfo.setOutTradeNo(OrderNoUtils.getOrderNo());
        orderInfo.setTradeBody(course.getTitle());
        orderInfo.setOrderStatus("0");
        baseMapper.insert(orderInfo);
        //6.2 封装数据到OrderDetail对象里面，添加订单详情信息表
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo.getId());
        orderDetail.setUserId(userId);
        orderDetail.setCourseId(courseId);
        orderDetail.setCourseName(course.getTitle());
        orderDetail.setCover(course.getCover());
        orderDetail.setOriginAmount(course.getPrice());
        orderDetail.setCouponReduce(new BigDecimal(0));
        orderDetail.setFinalAmount(orderDetail.getOriginAmount().subtract(orderDetail.getCouponReduce()));
        orderDetailService.save(orderDetail);

        //7 更新优惠券状态
        if(orderFormVo.getCouponUseId()!=null){
            couponInfoFeignClient.updateCouponInfoUseStatus(orderFormVo.getCouponUseId(), orderInfo.getId());
        }
        //8 返回订单id
        return orderInfo.getId();
    }

    //查询订单详情数据
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
        //订单id
        Long id = orderInfo.getId();
        //查询订单详情
        OrderDetail orderDetail = orderDetailService.getById(id);
        if(orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName",courseName);
        }
        return orderInfo;
    }


}
