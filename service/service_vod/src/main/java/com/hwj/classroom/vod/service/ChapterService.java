package com.hwj.classroom.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.vod.Chapter;
import com.hwj.classroom.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getTreeList(Long courseId);

    void removeChapterByCourseId(Long id);
}
