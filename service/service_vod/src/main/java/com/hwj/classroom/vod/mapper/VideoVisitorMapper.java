package com.hwj.classroom.vod.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwj.classroom.model.vod.VideoVisitor;
import com.hwj.classroom.vo.vod.VideoVisitorCountVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author hwj
 * @since 2023-11-03
 */

@Repository
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    List<VideoVisitorCountVo> findCount(@Param("courseId") Long courseId,
                                        @Param("startDate")String startDate,
                                        @Param("endDate")String endDate);
}
