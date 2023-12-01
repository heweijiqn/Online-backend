package com.hwj.classroom.vo.live;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LiveVisitorQueryVo {
	
	@ApiModelProperty(value = "直播课程id")
	private Long liveCourseId;

}

