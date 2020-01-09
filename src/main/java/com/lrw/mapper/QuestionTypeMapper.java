package com.lrw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.util.QueryVo;
import com.lrw.vo.QuestionType;

@Mapper
public interface QuestionTypeMapper {

	List<QuestionType> findAllQuestionTypeByUsername(QueryVo qv);

	void addQuestionType(QuestionType questioType);

	QuestionType findQuestionTypeByTid(Integer tid);

	void changeQTStatusToUnable(Integer tid);

	void changeQTStatusToAble(Integer tid);

	void changeQuestionTypeName(Integer tid, String name);

	List<QuestionType> queryAllEnableQT(String createuser);

	String findQuestionTypeNameByType(int type);

	QuestionType isRepeateQt(Map map);

	Integer findQuestionTypeByNameAndCreateUser(Map map);

}
