package com.lrw.service;

import java.util.List;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.QuestionBank;

public interface QuestionBankService {

	PageListRes findAllQuestionBankByUsername(QueryVo qv);

	void addQuestionBank(QuestionBank questioType);

	void changeQuestionBankName(Integer tid, String name);

	List<QuestionBank> queryAllEnableQB(String username);

	void changeQuestionBankToNoSeeByQbid(Integer qbid);

	void changeQuestionBankRemarks(Integer qbid, String remarks);

}
