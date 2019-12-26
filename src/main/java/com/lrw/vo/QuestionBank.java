package com.lrw.vo;

import com.lrw.util.GetTime;

import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel("题库实体类")
@Data
public class QuestionBank {

	private Integer qbid;
	private String qbname;
	private String createtime=GetTime.getFormatTime();//创建时间
	private String createuser;//创建人
	private Integer status=new Integer(0); //状态 0 ：用户可用 1：用户删除的，但是管理员可见
	private String remarks; //备注
	
}
