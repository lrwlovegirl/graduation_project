package com.lrw.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrw.mapper.QuestionMapper;
import com.lrw.service.QuestionService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Question;
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
	private QuestionMapper questionMapper;
	
    //查询操作，基本查询都走这个方法
    //keyword 默认为空
	@Override
	public PageListRes queryQuestionByKeyword(QueryVo qv) {
		PageListRes res = new PageListRes();
	    PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<Question> list = this.questionMapper.queryQuestionByKeyword(qv);
		PageInfo<Question> page = new PageInfo<Question>(list);
		res.setData(page.getList());
		//设置总条数
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public void createQuestion(Question question) {
		questionMapper.createQuestion(question);
	}

	@Override
	public Question findQuestionByQid(int qid) {
		return questionMapper.findQuestionByQid(qid);
	}

	@Override
	public void updateQuestion(Question question) {
		questionMapper.updateQuestion(question);
	}
	
	//根据问题类型查询
	@Override
	public PageListRes queryQuestionByType(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<Question> list = this.questionMapper.queryQuestionByType(qv);
		PageInfo<Question> page = new PageInfo<Question>(list);
		res.setData(page.getList());
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public int findSelectQuestion() {
		List<Question> selectQuestionList = new ArrayList();
		return 0;
	}

	@Override
	public List<Question> selectQuestionByQids(@NotNull Integer[] qids) {
		// TODO Auto-generated method stub
		return questionMapper.selectQuestionByQids(qids);
	}
	

}
