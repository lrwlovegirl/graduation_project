package com.lrw.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Question;

public interface QuestionService {

	//模糊查询，默认查询全部
	PageListRes queryQuestionByKeyword(QueryVo qv);
    //创造问题
	void createQuestion(Question question); 
	//查询出问题
	Question findQuestionByQid(int Qid);
	//更新问题
	void updateQuestion(Question question);
    //查询出某一类型问题
	PageListRes queryQuestionByType(QueryVo qv);
	//查出选择题的数量
	int findSelectQuestion();
	
	List<Question> selectQuestionByQids(@NotNull Integer[] qids);
	
	void createQuestionList(List<Question> questionList);
	//根据查询出所有的试题，包括其他用户上传的
	List<Question> findAllQuestionByType(int type);
	
	
  

}
