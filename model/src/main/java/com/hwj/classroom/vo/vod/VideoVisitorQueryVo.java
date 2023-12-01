package com.hwj.classroom.vo.vod;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VideoVisitorQueryVo {
	
	@ApiModelProperty(value = "课程id")
	private Long courseId;

	@ApiModelProperty(value = "视频id")
	private Long videoId;

	@ApiModelProperty(value = "进入时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;
}

