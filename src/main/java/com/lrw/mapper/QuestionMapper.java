package com.lrw.mapper;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Question;

@Mapper
public interface QuestionMapper {

	//模糊查询，默认查询全部
	List<Question> queryQuestionByKeyword(QueryVo qv);

	void createQuestion(Question question); 

	//通过id查询出单个用户
	Question findQuestionByQid(int Qid);
	//更新单个用户
	void updateQuestion(Question question);
	
	List<Question> queryQuestionByType(QueryVo qv);

	List<Question> selectQuestionByQids (@NotNull Integer[] qids);

	void insertQuestionList(@Param("questionList")List questionList);

	List<Question> findAllQuestionByType(int type);

	List<Question> selectBatchQuestionByTypeAndTpid(String tpid, int type);




}
