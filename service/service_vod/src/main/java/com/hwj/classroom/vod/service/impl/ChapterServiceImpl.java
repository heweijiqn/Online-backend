package com.hwj.classroom.vod.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwj.classroom.model.vod.Chapter;
import com.hwj.classroom.model.vod.Video;
import com.hwj.classroom.vo.vod.ChapterVo;
import com.hwj.classroom.vo.vod.VideoVo;
import com.hwj.classroom.vod.mapper.ChapterMapper;
import com.hwj.classroom.vod.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.vod.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getTreeList(Long courseId) {
        //定义最终数据list集合
        List<ChapterVo> finalChapterList = new ArrayList<>();

        //根据courseId获取课程里面所有章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);

        //根据courseId获取课程里面所有小节
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(Video::getCourseId, courseId);
        List<Video> videoList = videoService.list(videoLambdaQueryWrapper);

        // 封装章节
        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo); // 使用BeanUtils进行属性拷贝

            // 封装小节
            List<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videoList) {
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList); // 设置小节列表为章节的子节点

            finalChapterList.add(chapterVo); // 将章节添加到最终列表中
        }

        return finalChapterList;
    }

    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        baseMapper.delete(chapterQueryWrapper);

    }
}
