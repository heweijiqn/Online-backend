package com.hwj.classroom.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwj.classroom.model.vod.Subject;
import com.hwj.classroom.vo.vod.SubjectEeVo;
import com.hwj.classroom.vod.listener.SubjectListener;
import com.hwj.classroom.vod.mapper.SubjectMapper;
import com.hwj.classroom.vod.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwj.exception.HwjException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;
    /*
    * 递归查询子节点
    * @param id
     */
    @Override
    public List<Subject> selectSubjectList(Long id) {
        //SELECT * FROM SUBJECT WHERE parent_id=0
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Subject> subjectList = baseMapper.selectList(wrapper);  //查询出所有子节点
        //subjectList遍历，得到每个subject对象，判断是否有下一层数据，有hasChildren=true
        for (Subject subject:subjectList) {
            Long subjectId = subject.getId();
            //查询
            boolean isChild = this.isChildren(subjectId);
            //封装到对象里面
            subject.setHasChildren(isChild);
        }
        return subjectList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

            //查询课程分类表所有数据
            List<Subject> subjectList = baseMapper.selectList(null);

            List<SubjectEeVo> subjectEeVoList = new ArrayList<>();
            for (Subject subject: subjectList) {
                SubjectEeVo subjectEeVo = new SubjectEeVo();

                BeanUtils.copyProperties(subject,subjectEeVo);  //复制属性
                subjectEeVoList.add(subjectEeVo);
            }

            //EasyExcel写操作
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVoList);
        }catch(Exception e) {
            throw new HwjException(20001,"导出失败");
        }
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),
                    SubjectEeVo.class,
                    subjectListener).sheet().doRead();
        } catch (IOException e) {
            throw new HwjException(20001,"导入失败");
        }
    }

    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        return count>0;
    }
}
