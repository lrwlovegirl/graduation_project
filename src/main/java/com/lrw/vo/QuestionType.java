package com.lrw.vo;

import com.lrw.util.GetTime;

import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel("题型实体类")
@Data
public class QuestionType {
	private Integer tid;
	private int status;
	private String createuser;//创建人
	private String name;
	private String createtime=GetTime.getFormatTime();//创建时间
}
