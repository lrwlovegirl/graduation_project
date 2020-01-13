package com.lrw.vo;

import lombok.Data;
//这个实体类主要是为了做过渡作用，记住随机生成的题目编号和类型
@Data
public class QuestionTidAndType {

	private Integer tid;
	private Integer type;
	private Integer score;
	private String tpid;
	
}
