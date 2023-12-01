package com.hwj.classroom.live.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hwj.classroom.live.mapper.LiveCourseAccountMapper;
import com.hwj.classroom.live.service.LiveCourseAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.model.live.LiveCourseAccount;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 直播课程账号表（受保护信息） 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
@Service
public class LiveCourseAccountServiceImpl extends ServiceImpl<LiveCourseAccountMapper, LiveCourseAccount> implements LiveCourseAccountService {

    @Override
    public LiveCourseAccount getLiveCourseAccountCourseId(Long id) {
        LambdaQueryWrapper<LiveCourseAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LiveCourseAccount::getLiveCourseId,id);
        LiveCourseAccount liveCourseAccount = baseMapper.selectOne(wrapper);
        return liveCourseAccount;
    }
}
