package com.lrw.vo;

import com.lrw.util.GetTime;

import lombok.Data;
//这个实体类主要是为了做过渡作用，记住随机生成的题目编号和类型
@Data
public class QuestionTidAndType {
    
	private Integer tid; //题目编号
	private Integer type; //题目类型
	private Integer score;//分数
	private String tpid;//所属试卷
	private String createtime = GetTime.getFormatTime();
	
}
