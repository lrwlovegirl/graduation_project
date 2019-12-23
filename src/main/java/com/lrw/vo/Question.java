package com.lrw.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.DecimalFormat;

import lombok.Data;
//题列表
@Data
public class Question implements Serializable{
   
	private Integer qid;//问题id
	private String title;//问题
	private String content;//问题具体描述，要求
	private String about;//问题是关于什么的，相当于标签
	private String answer;//问题答案
    private int type;//问题的类型 0:选择题  1:填空题  2:设计题 
    private int  correctCount; //本题回答正确的人数
    private int  uncorrentCount;//回答错误的人数
    private String birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//生成日期
	private String publisher ="admin";
	//掌握程度
	private String masteryLevel = (correctCount==0&&uncorrentCount==0) ? "0.00":new DecimalFormat("0.00").format((float)correctCount/(correctCount+uncorrentCount));
	//修改次数，每次修改都应该有个记录，这里先不做，有时间在做
	private int difficultyLevel = 0; //本题难易程度 0：初级   1：中级   2：高级 
	private String optionA;//选项A
	private String optionB;//选项B
	private String optionC;//选项C
	private String optionD;//选项D
	private String analysis="~本题目没有解析呦~";//题目解析
	
	
	
}
