package com.lrw.service;

import java.util.List;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.QuestionType;

public interface QuestionTypeService {

	public PageListRes findAllQuestionTypeByUsername(QueryVo qv);

	public void addQuestionType(QuestionType questioType);

	public QuestionType findQuestionTypeByTid(Integer tid);

	public void changeQTStatusToAble(Integer tid);

	public void changeQTStatusToUnable(Integer tid);

	public void changeQuestionTypeName(Integer tid, String name);

	public List<QuestionType> queryAllEnableQT(String username);

	public String findQuestionTypeNameByType(int type);
	
	boolean isRepeateQt(String typename,String username);

	public Integer findQuestionTypeByNameAndCreateUser(String questiontype, String username);
	
	
}
