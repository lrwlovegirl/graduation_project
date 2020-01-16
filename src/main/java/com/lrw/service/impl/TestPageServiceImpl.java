package com.lrw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graphbuilder.struc.LinkedList;
import com.lrw.mapper.QuestionMapper;
import com.lrw.mapper.TestPageMapper;
import com.lrw.service.TestPageService;
import com.lrw.util.NumberUtils;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Question;
import com.lrw.vo.QuestionTidAndType;
import com.lrw.vo.TestPage;
@Service
public class TestPageServiceImpl implements TestPageService {

	@Autowired
	private TestPageMapper testPageMapper; 
	@Autowired
	private QuestionMapper questionMapper;
	
	/**
	 * 
	 * @param 检查试卷名称是否相同
	 */
	public boolean isRepeatTopicName(String topic,String username) {
		return testPageMapper.isRepeatTopicName(topic,username) == null;
	}

	@Override
	public PageListRes queryAllTestPageByUserName(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<TestPage> list = this.testPageMapper.queryAllTestPageByUserName(qv.getPublisher());
		PageInfo<TestPage> page = new PageInfo<TestPage>(list);
		res.setData(page.getList());
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
		
	}
	//进行试卷的生成，将题目按题型打包，封装为List，最后返回一个List<List>
	//questionTypeNumbers   每种题型需要的数量
	//questionScore         每种题型的分值
	//tids                  对应题型的id数组
    @Override
	public boolean addRandomTestPage(String tpid,Integer[] questionTypeNumbers, Integer[] questionScore,Integer[] tids) {
		//最后要返回的对像
    	List<QuestionTidAndType> list = new ArrayList<QuestionTidAndType>();
    	for(int x=0;x<tids.length;x++) {
    		if(questionTypeNumbers[x]>0) {
    			int type = tids[x];
    			List<Question> questionList = questionMapper.findAllQuestionByType(type);
    			if(questionList==null) {questionList=new ArrayList<>();}
    			if(questionTypeNumbers[x]>questionList.size()) {//超出了题库这个用户的范围
    				for(int y=0;y<questionList.size();y++) {
    					QuestionTidAndType vo = new QuestionTidAndType();
    					Question question = questionList.get(y);
    					vo.setTid(question.getQid());
    					vo.setType(question.getType());
    					vo.setScore(questionScore[x]);
    					vo.setTpid(tpid);
    					list.add(vo);
    				}
    			}else {//没有超过
    				//随机取出x条
					//这里取出的是在List中的索引号
    				List<Integer> randomNumberList = NumberUtils.getRandomNumberList(questionList.size(), questionTypeNumbers[x]);
					//获取随机的该类型的题目
					for (int i = 0; i < randomNumberList.size(); i++) {
						int index = randomNumberList.get(i);
						Question question = questionList.get(index);
						QuestionTidAndType vo = new QuestionTidAndType();
						vo.setScore(questionScore[x]);
						vo.setType(type);
						vo.setTid(question.getQid());
						vo.setTpid(tpid);
						list.add(vo);
					}
    			}
    		}
    	}
		try {
			testPageMapper.addRealRandTestPage(list);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addRandTestPage(TestPage testPage) {
		testPageMapper.addRandTestPage(testPage);
	}

	@Override
	public TestPage seeTestPaperDetailsByTpid(String tpid) {
		TestPage testPage = testPageMapper.findTestPaperByTpid(tpid);
		if(testPage == null ) {testPage = new TestPage();}
		List<List<Question>> questionList = new ArrayList<>();
		List<String> explainList = new ArrayList<>();
		int len = testPageMapper.findTypeLenBytpid(tpid);//试卷中类型数量
		List<Integer> typeList = new ArrayList<>();
		if(len>0) {typeList = testPageMapper.findTypeListByTpid(tpid);}//查询出所有的类型
		//根据类型和tpid查询出题目
		for(int x=0;x<typeList.size();x++) {
			int type = typeList.get(x);
			List<Question> batchList = questionMapper.selectBatchQuestionByTypeAndTpid(tpid,type);
			String explain = "本题为"+batchList.get(0).getQuestiontype()+",每题"+batchList.get(0).getScore()+"分";
			explainList.add(explain);
			questionList.add(batchList);
		}
		testPage.setQuestionList(questionList);
		testPage.setTitleExplain(explainList);
		return testPage;
	}

	@Override
	public void deleteTestPaperByTpid(String tpid) {
		testPageMapper.deleteTestPaperByTpid(tpid);
	}

	@Override
	public TestPage artificalTestPaper(Integer[] qt,String username,String topic,String uuid) {
		List<List<Question>> questionList = new java.util.LinkedList<List<Question>>();
		List<String> explainList = new java.util.LinkedList<String>();
		for(int x=0;x<qt.length;x++) {
			List<Question> realquestionList = questionMapper.findAllQuestionByType(qt[x]);
			if(!realquestionList.isEmpty()) {
				questionList.add(realquestionList);
				String explain =  "";
				if(realquestionList.get(0).getQuestiontype().contains("选")) {
					explain = "已为您列出所有的"+realquestionList.get(0).getQuestiontype()+",请选择具体题目(选择题部分选项过长不予显示)：";
				}else {
					explain = "已为您列出所有的"+realquestionList.get(0).getQuestiontype()+",请选择具体题目：";
				}
				explainList.add(explain);
			}
		}
		TestPage testPage = new TestPage();
		testPage.setTpid(uuid);
		testPage.setCreatetype(1);
		testPage.setTopic(topic);
		testPage.setCreateuser(username);
		testPage.setTitleExplain(explainList);
		testPage.setQuestionList(questionList);
		//要等到用户选好了才能去保存
//		try {
//			testPageMapper.addRandTestPage(testPage);
//		}catch (Exception e) {
//			e.printStackTrace();
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚 
//		}
		return testPage;
	}
	
	
	
	
}
