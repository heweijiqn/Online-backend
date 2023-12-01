package com.hwj.classroom.wechat.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.wechat.Menu;
import com.hwj.classroom.vo.wechat.MenuVo;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-07
 */
public interface MenuService extends IService<Menu> {

    List<Menu> findMenuOneInfo();

    List<MenuVo> findMenuInfo();

    void syncMenu();

    void removeMenu();
}
