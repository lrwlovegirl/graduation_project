package com.lrw.vo;

import java.util.List;

import com.lrw.util.GetTime;
import com.lrw.util.Num2CN;

import lombok.Data;

@Data
public class TestPage {
	private String tpid; 
	private String createuser;
	private String createtime = GetTime.getFormatTime();
	private String topic;//试卷的标题
	private List<String> titleExplain;//题目注释
	private List<List<Question>> questionList;//将每种题型分别存储
	
	public StringBuffer exportTestPage() { 
		StringBuffer sbf = new StringBuffer("                             "+topic+"\r\n"+"                                               ");
		if(createuser!=null) {sbf.append("出题人："+createuser+"\r\n");}
		for(int x=0;x<titleExplain.size();x++) {//有几种题型
			//用了工具类，将数字转换成大写汉字 例如 10 十  11 十一
			sbf.append(Num2CN.cvt((x+1), true)+"、"+titleExplain.get(x)+"\r\n");//题目要求   本题为xx题，每题多少分
			List<Question> typeQuestionList = questionList.get(x);
			for(int y=0;y<typeQuestionList.size();y++) {
				Question vo = typeQuestionList.get(y);
				sbf.append((y+1)+". "+vo.getTitle()+"\r\n");
				if(vo.getOptionA()!=null) {
					sbf.append("A. "+vo.getOptionA()+"\r\n");
				}
				if(vo.getOptionB()!=null) {
					sbf.append("B. "+vo.getOptionB()+"\r\n");
				}
				if(vo.getOptionC()!=null) {
					sbf.append("C. "+vo.getOptionC()+"\r\n");
				}
				if(vo.getOptionD()!=null) {
					sbf.append("D. "+vo.getOptionD()+"\r\n\r\n");
				}
			}
		}
		return sbf;
	}
	
}
