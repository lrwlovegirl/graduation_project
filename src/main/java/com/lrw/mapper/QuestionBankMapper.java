package com.lrw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.util.QueryVo;
import com.lrw.vo.QuestionBank;

@Mapper
public interface QuestionBankMapper {

	List<QuestionBank> findAllQuestionBankByUsername(QueryVo qv);

	void addQuestionBank(QuestionBank questionBank);

	void changeQuestionBankName(Integer qbid, String qbname);

	List<QuestionBank> queryAllEnableQB(String username);

	void changeQuestionBankToNoSeeByQbid(Integer qbid);

	void changeQuestionBankRemarks(Integer qbid, String remarks);

	List<QuestionBank> selectAllEnableQBByUserName(String username);

	QuestionBank isRepeateName(String name, String username);

}
