package com.lrw.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
	 * 要处理试卷，将一张试卷的所有格式和内容都处理好
	 * @param randomQuestionList 已经处理好的题型
	 */
	public void handleTestPage(List<Question> randomQuestionList) {
		
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
	public List<QuestionTidAndType> addRandomTestPage(String tpid,Integer[] questionTypeNumbers, Integer[] questionScore,Integer[] tids) {
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
		return list;
	}

	@Override
	public void addRandTestPage(TestPage testPage) {
		testPageMapper.addRandTestPage(testPage);
	}
	
	
	
	
}
