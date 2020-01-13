package com.lrw.service;

import java.util.List;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Question;
import com.lrw.vo.TestPage;

//处理试卷
public interface TestPageService {

	PageListRes queryAllTestPageByUserName(QueryVo qv);
	
	List addRandomTestPage(String tpid,Integer[] questionTypeNumbers,Integer[] questionScore,Integer[] tids);
	
	void addRandTestPage(TestPage testPage);
}
