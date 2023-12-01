package com.hwj.classroom.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.model.user.UserInfo;
import com.hwj.classroom.user.mapper.UserInfoMapper;
import com.hwj.classroom.user.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-04-29
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    @Override
    public UserInfo getUserInfoOpenid(String openId) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        UserInfo userInfo = baseMapper.selectOne(wrapper);
        return userInfo;
    }
}
