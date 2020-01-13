package com.lrw.vo;

import java.util.List;

import com.lrw.util.GetTime;

import lombok.Data;

@Data
public class TestPage {
	private String tpid; 
	private String createuser;
	private String createtime = GetTime.getFormatTime();
	private String topic;//试卷的标题
	private List<String> titleExplain;//题目注释
	private List<List<Question>> questionList;//将每种题型分别存储
	
}
