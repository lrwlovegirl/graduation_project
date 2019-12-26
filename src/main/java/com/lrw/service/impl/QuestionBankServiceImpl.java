package com.lrw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrw.mapper.QuestionBankMapper;
import com.lrw.service.QuestionBankService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Kpoint;
import com.lrw.vo.QuestionBank;
@Service
public class QuestionBankServiceImpl implements QuestionBankService {

	@Autowired
	private QuestionBankMapper questionBankMapper;
	
	
	@Override
	public PageListRes findAllQuestionBankByUsername(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<QuestionBank> list = this.questionBankMapper.findAllQuestionBankByUsername(qv);
		PageInfo<QuestionBank> page = new PageInfo<QuestionBank>(list);
		res.setData(list);
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public void addQuestionBank(QuestionBank questionBank) {
		// TODO Auto-generated method stub
		questionBankMapper.addQuestionBank(questionBank);
	}

	@Override
	public void changeQuestionBankName(Integer qbid, String qbname) {
		// TODO Auto-generated method stub
		questionBankMapper.changeQuestionBankName(qbid,qbname);
	}

	@Override
	public List<QuestionBank> queryAllEnableQB(String username) {
		
		return questionBankMapper.queryAllEnableQB(username);
	}

	@Override
	public void changeQuestionBankToNoSeeByQbid(Integer qbid) {
		// TODO Auto-generated method stub
		questionBankMapper.changeQuestionBankToNoSeeByQbid(qbid);
	}

	@Override
	public void changeQuestionBankRemarks(Integer qbid, String remarks) {
		// TODO Auto-generated method stub
		questionBankMapper.changeQuestionBankRemarks(qbid,remarks);
	}

}
