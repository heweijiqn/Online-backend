package com.hwj.classroom.wechat.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwj.classroom.model.wechat.Menu;
import com.hwj.classroom.vo.wechat.MenuVo;
import com.hwj.classroom.wechat.mapper.MenuMapper;
import com.hwj.classroom.wechat.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.exception.HwjException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Autowired
    private WxMpService wxMpService;

    /**
     * 同步菜单
     * 微信菜单最多3个一级菜单，每个一级菜单最多5个二级菜单
     * 每个二级菜单最多10个三级菜单
     * 每个三级菜单最多5个按钮
     *
     */
    @Override
    public void syncMenu() {
        //获取所有菜单数据
        List<MenuVo> menuVoList = this.findMenuInfo();
        //封装button里面结构，数组格式
        JSONArray buttonList = new JSONArray();
        for (MenuVo oneMenuVo:menuVoList) {
            //json对象  一级菜单
            JSONObject one = new JSONObject();
            one.put("name",oneMenuVo.getName());
            //json数组   二级菜单
            JSONArray subButton = new JSONArray();
            for (MenuVo twoMenuVo:oneMenuVo.getChildren()) {
                JSONObject view = new JSONObject();
                view.put("type", twoMenuVo.getType());
                if(twoMenuVo.getType().equals("view")) {
                    view.put("name", twoMenuVo.getName());
                    view.put("url", "https://828p801z80.goho.co/#"
                            +twoMenuVo.getUrl());
                } else {
                    view.put("name", twoMenuVo.getName());
                    view.put("key", twoMenuVo.getMeunKey());
                }
                subButton.add(view);
            }
            one.put("sub_button",subButton);
            buttonList.add(one);
        }
        //封装最外层button部分
        JSONObject button = new JSONObject();
        button.put("button",buttonList);

        try {
            String menuId =
                    this.wxMpService.getMenuService().menuCreate(button.toJSONString());
            System.out.println("menuId"+menuId);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new HwjException(20001,"公众号菜单同步失败");
        }
    }

    @Override
    public void removeMenu() {
        try {
            wxMpService.getMenuService().menuDelete();
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询一级菜单
     * @return
     */
    @Override
    public List<Menu> findMenuOneInfo() {
        QueryWrapper <Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",0);
        final List<Menu> menus = baseMapper.selectList(queryWrapper);
        return  menus;

    }

    @Override
    public List<MenuVo> findMenuInfo() {

        //1 创建list集合，用户最终数据封装
        List<MenuVo> finalMenuList = new ArrayList<>();
        //2 查询所有菜单数据（包含一级和二级）
        List<Menu> menuList = baseMapper.selectList(null);
        //3 从所有菜单数据获取所有一级菜单数据（parent_id=0）
        List<Menu> oneMenuList = menuList.stream()
                            .filter(menu -> menu.getParentId().longValue() == 0)
                            .collect(Collectors.toList());
        //4 封装一级菜单数据，封装到最终数据list集合
        //遍历一级菜单list集合
        for (Menu oneMenu : oneMenuList) {
            //1 创建一级菜单对象
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(oneMenu,menuVo);

            //5 封装二级菜单数据（判断一级菜单id和二级菜单parent_id是否相同）
            //如果相同，把二级菜单数据放到一级菜单里面
            List<Menu> twoMenuList = menuList.stream()
                    .filter(menu -> menu.getParentId().longValue() == oneMenu.getId())
                    .collect(Collectors.toList());
            List<MenuVo> children = new ArrayList<>();
            for (Menu twoMenu:twoMenuList) {
                MenuVo twoMenuVo = new MenuVo();
                BeanUtils.copyProperties(twoMenu,twoMenuVo);
                children.add(twoMenuVo);
            }
            menuVo.setChildren(children);
            finalMenuList.add(menuVo);
        }
        return finalMenuList;
    }


}
