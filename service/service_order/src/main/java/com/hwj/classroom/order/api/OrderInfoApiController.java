/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/11/12 16:14
 */


package com.hwj.classroom.order.api;

import com.hwj.classroom.order.service.OrderInfoService;
import com.hwj.classroom.vo.order.OrderFormVo;
import com.hwj.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order/orderInfo")
public class OrderInfoApiController {

    @Autowired
    private OrderInfoService orderInfoService;

    /*
     * 生成订单
     * @param orderFormVo
     * @return
     */
    @PostMapping("submitOrder")
    public Result submitOrder(@RequestBody OrderFormVo orderFormVo) {
        Long orderId = orderInfoService.submitOrder(orderFormVo);
        return Result.ok(orderId);
    }
}
