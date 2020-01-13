package com.lrw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.vo.QuestionTidAndType;
import com.lrw.vo.TestPage;

@Mapper
public interface TestPageMapper {

	List<TestPage> queryAllTestPageByUserName(String username);

	void addRandTestPage(TestPage testPage);
	
	void addRealRandTestPage(List<QuestionTidAndType> list);

	TestPage isRepeatTopicName(String topic, String username);

	TestPage findTestPaperByTpid(String tpid);

	List<QuestionTidAndType> findQidByTpidAndType(String tpid,int type);

	int findTypeLenBytpid(String tpid);

	List<Integer> findTypeListByTpid(String tpid);
}
