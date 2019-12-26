package com.lrw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrw.mapper.QuestionTypeMapper;
import com.lrw.service.QuestionTypeService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Question;
import com.lrw.vo.QuestionType;
@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {

	@Autowired
	private QuestionTypeMapper questionTypeMapper;
	
	
	
	@Override
	public PageListRes findAllQuestionTypeByUsername(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<QuestionType> list = this.questionTypeMapper.findAllQuestionTypeByUsername(qv);
		PageInfo<QuestionType> page = new PageInfo<QuestionType>(list);
		res.setData(list);
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}



	@Override
	public void addQuestionType(QuestionType questioType) {
		questionTypeMapper.addQuestionType(questioType);
	}



	@Override
	public QuestionType findQuestionTypeByTid(Integer tid) {
		return questionTypeMapper.findQuestionTypeByTid(tid);
	}



	@Override
	public void changeQTStatusToAble(Integer tid) {
		// TODO Auto-generated method stub
		questionTypeMapper.changeQTStatusToAble(tid);
	}



	@Override
	public void changeQTStatusToUnable(Integer tid) {
		// TODO Auto-generated method stub
		questionTypeMapper.changeQTStatusToUnable(tid);
	}



	@Override
	public void changeQuestionTypeName(Integer tid, String name) {
		questionTypeMapper.changeQuestionTypeName(tid,name);
	}



	@Override
	public List<QuestionType> queryAllEnableQT(String username) {
		// TODO Auto-generated method stub
		return questionTypeMapper.queryAllEnableQT(username);
	}



	@Override
	public String findQuestionTypeNameByType(int type) {
		// TODO Auto-generated method stub
		return questionTypeMapper.findQuestionTypeNameByType(type);
	}

}
