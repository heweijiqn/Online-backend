package com.hwj.classroom.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.classroom.model.vod.Course;
import com.hwj.classroom.model.vod.CourseDescription;
import com.hwj.classroom.model.vod.Subject;
import com.hwj.classroom.model.vod.Teacher;
import com.hwj.classroom.vo.vod.*;
import com.hwj.classroom.vod.mapper.CourseMapper;
import com.hwj.classroom.vod.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;


    @Autowired
    private CourseDescriptionService descriptionService;

    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        //添加课程基本信息，操作course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.insert(course);

        //添加课程描述信息，操作course_description

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        //设置课程id
        courseDescription.setId(course.getId());
        courseDescription.setCourseId(course.getId());
        descriptionService.save(courseDescription);

        //设置课程id
        return course.getId();
    }

    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();//二层分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一层分类
        Long teacherId = courseQueryVo.getTeacherId();  //讲师id
        //判断条件为空，封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }

        //调用方法实现条件查询分页
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long totalPage = pages.getPages();
        List<Course> list = pages.getRecords();
        //查询数据里面有几个id
        //讲师id  课程分类id（一层 和 二层）
        //获取这些id对应名称，进行封装，最终显示
        list.stream().forEach(item -> {
            this.getNameById(item);
        });  // stream流  链式编程
        //封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",list);
        return map;
    }
    //获取这些id对应名称，进行封装，最终显示
    private Course getNameById(Course course) {
        //根据讲师id获取讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher != null) {
            String name = teacher.getName();
            course.getParam().put("teacherName",name);
        }

        //根据课程分类id获取课程分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null) {
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }
        return course;
    }

    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        //课程基本信息
        Course course = baseMapper.selectById(id);
        if(course == null) {
            return null;
        }
        //课程描述信息
        CourseDescription courseDescription = descriptionService.getById(id);
        //封装
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        //封装描述
        if(courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {

        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.updateById(course);
        //修改课程描述信息
        CourseDescription description = new CourseDescription();
        description.setDescription(courseFormVo.getDescription());
        //设置课程描述id
        description.setId(course.getId());
        descriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);//已经发布
        course.setPublishTime(new Date());
        baseMapper.updateById(course);

    }

    @Override
    public void removeCourseId(Long id) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(id);

        //根据课程id删除章节
        chapterService.removeChapterByCourseId(id);

        //根据课程id删除课程描述
        descriptionService.removeById(id);

        //根据课程id删除课程
        baseMapper.deleteById(id);

    }

    @Override
    public List<Course> findlist() {
        List<Course> list = baseMapper.selectList(null);
        list.stream().forEach(item -> {
            this.getTeacherAndSubjectName(item);
        });
        return list;
    }

    private Course getTeacherAndSubjectName(Course course) {
        //讲师名称
        Long teacherId = course.getTeacherId();
        Teacher teacher = teacherService.getById(teacherId);
        if(teacher != null) {
            course.getParam().put("teacherName",teacher.getName());
        }
        //课程分类名称
        Long subjectParentId = course.getSubjectParentId();
        Subject oneSubject = subjectService.getById(subjectParentId);
        if(oneSubject != null) {
            course.getParam().put("subjectParentTitle",oneSubject.getTitle());
        }
        Long subjectId = course.getSubjectId();
        Subject twoSubject = subjectService.getById(subjectId);
        if(twoSubject != null) {
            course.getParam().put("subjectTitle",twoSubject.getTitle());
        }
        return course;
    }

    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        //获取条件值
        String title = courseQueryVo.getTitle();//名称
        Long subjectId = courseQueryVo.getSubjectId();//二级分类
        Long subjectParentId = courseQueryVo.getSubjectParentId();//一级分类
        Long teacherId = courseQueryVo.getTeacherId();//讲师
        //封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id",teacherId);
        }
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);

        long totalCount = pages.getTotal();//总记录数
        long totalPage = pages.getPages();//总页数
        long currentPage = pages.getCurrent();//当前页
        long size = pages.getSize();//每页记录数
        //每页数据集合
        List<Course> records = pages.getRecords();
        //stream流  链式编程 封装其他数据（获取讲师名称 和 课程分类名称）
        records.stream().forEach(item -> {
            this.getNameById(item);
        });
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);

        return map;
    }

    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        //view_count浏览数量 +1
        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);
        //根据课程id查询
        //课程详情数据
        CourseVo courseVo = baseMapper.selectCourseVoById(courseId);
        //课程章节小节数据
        List<ChapterVo> chapterVoList = chapterService.getTreeList(courseId);
        //课程描述信息
        CourseDescription courseDescription = descriptionService.getById(courseId);
        //课程所属讲师信息
        Teacher teacher = teacherService.getById(course.getTeacherId());

        //封装map集合，返回
        Map<String,Object> map = new HashMap();
        map.put("courseVo", courseVo);
        map.put("chapterVoList", chapterVoList);
        map.put("description", null != courseDescription ? courseDescription.getDescription() : "");
        map.put("teacher", teacher);
        /* TODO: 1、判断课程是否免费 2、判断当前用户是否已经购买这个课程，是否完成支付
            判断课程表的字段price，如果课程价格是0，课程免费
            判断当前用户是否已经购买过这个课程，如果已经购买过，返回true，否则返回false
            查询订单基本信息表和订单详情表
            SELECT * EROM
            order info o1,order detail o2 WHERE ol.id=o2.id AND o1.user_id=59 AND o2.course_id=2 AND ol.order_status=1
            这个sql语句查询出来的结果，如果有数据，说明当前用户已经购买过这个课程，返回true，否则返回false
         */
        map.put("isBuy", false);//是否购买
        return map;

    }
}
