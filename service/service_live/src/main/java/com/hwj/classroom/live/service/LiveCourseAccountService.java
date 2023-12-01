package com.hwj.classroom.live.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.live.LiveCourseAccount;

/**
 * <p>
 * 直播课程账号表（受保护信息） 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-14
 */
public interface LiveCourseAccountService extends IService<LiveCourseAccount> {

    LiveCourseAccount getLiveCourseAccountCourseId(Long id);
}
