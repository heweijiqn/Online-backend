package com.hwj.classroom.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hwj.classroom.model.vod.Subject;
import com.hwj.classroom.vo.vod.SubjectEeVo;
import com.hwj.classroom.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {

    //注入mapper
    @Autowired
    private SubjectMapper subjectMapper;

    //一行一行，从第二行
    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo,subject);
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
