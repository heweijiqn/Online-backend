package com.hwj.classroom.order.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.order.OrderInfo;
import com.hwj.classroom.vo.order.OrderFormVo;
import com.hwj.classroom.vo.order.OrderInfoQueryVo;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-04
 */
public interface OrderInfoService extends IService<OrderInfo> {



    Map<String, Object> selectOrderInfoPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    Long submitOrder(OrderFormVo orderFormVo);
}
