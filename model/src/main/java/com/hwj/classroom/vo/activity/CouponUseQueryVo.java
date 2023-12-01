package com.hwj.classroom.vo.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CouponUseQueryVo {
	
	@ApiModelProperty(value = "购物券ID")
	private Long couponId;
	@ApiModelProperty(value = "购物券状态（1：未使用 2：已使用）")
	private String couponStatus;

	@ApiModelProperty(value = "获取时间")
	private String getTimeBegin;

	@ApiModelProperty(value = "使用时间")
	private String getTimeEnd;

}

