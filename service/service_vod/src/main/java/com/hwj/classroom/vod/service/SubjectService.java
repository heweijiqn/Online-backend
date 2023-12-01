package com.hwj.classroom.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hwj.classroom.model.vod.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hwj
 * @since 2023-11-02
 */
public interface SubjectService extends IService<Subject> {

    List<Subject> selectSubjectList(Long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
