package com.lrw.vo;

import com.lrw.util.GetTime;

import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel("知识点实体类")
@Data
public class Kpoint {
	private Integer kid ;//编号
	private String kname;//名称
	private String createtime = GetTime.getFormatTime();
	private String createuser;//创建人
	private String kpointexplain;
	private Boolean mark;
}
