package com.lrw.service;

import java.util.List;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.Question;
import com.lrw.vo.TestPage;

//处理试卷
public interface TestPageService {

	PageListRes queryAllTestPageByUserName(QueryVo qv);
	
	boolean addRandomTestPage(String tpid,Integer[] questionTypeNumbers,Integer[] questionScore,Integer[] tids);
	
	void addRandTestPage(TestPage testPage);

	boolean isRepeatTopicName(String topic, String username);

	TestPage seeTestPaperDetailsByTpid(String tpid);

	void deleteTestPaperByTpid(String tpid);

	TestPage artificalTestPaper(Integer[] qt,String username,String topic,String uuid);
	
	public boolean saveArtificalTpQuestionList(String uuid,List<Question> questionList,TestPage testPage);
}
