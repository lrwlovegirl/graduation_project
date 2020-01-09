package com.lrw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.QuestionService;
import com.lrw.service.QuestionTypeService;
import com.lrw.util.NumberUtils;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.Question;

@RestController
@RequestMapping("/Question")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class QuestionController {
	@Autowired
	private QuestionService questionServiceimpl;

	@Autowired
	private QuestionTypeService questionTypeServiceImpl;
	//存放试卷的
	private Map<String,List<Question>> randomQuestionMap = new ConcurrentHashMap<>() ;


	//模糊查询，默认查询全部
	@PostMapping("/queryQuestionByKeyword")
	public PageListRes queryQuestionByKeyword(QueryVo qv) {
		PageListRes res = null;
		try {
			res = questionServiceimpl.queryQuestionByKeyword(qv);
		}catch (Exception e) {
			e.printStackTrace();
			res = new PageListRes();
		}
		return res;
	}
	//创造问题
	@PostMapping("/createQuestion")
	public ReturnRes createQuestion(Question question) {
		ReturnRes res = new ReturnRes();
		try {
			int type = question.getType();
			String typename = questionTypeServiceImpl.findQuestionTypeNameByType(type);
			question.setQuestiontype(typename);
			//System.out.println(question);
			questionServiceimpl.createQuestion(question);
			res.setSuccess(true);
			res.setMsg("添加成功");
		}catch (Exception e) {
			res.setSuccess(false);
			res.setMsg("添加失败，请稍后再试");
		}
		return res;
	}


	//查询出单个问题
	@PostMapping("/findQuestionByQid")
	public Question findQuestionByQid(int Qid) {
		Question question = null;
		try {
			question = questionServiceimpl.findQuestionByQid(Qid);
		}catch (Exception e) {
			e.printStackTrace();
			question = new Question();
		}
		return question;
	}
	//更新问题
	@PostMapping("/updateQuestion")
	public ReturnRes updateQuestion(Question question) {
		ReturnRes res = new ReturnRes();
		try {
			questionServiceimpl.updateQuestion(question);
			res.setSuccess(true);
			res.setMsg("更新成功");
		}catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
			res.setMsg("更新失败，请稍后再试");
		}
		return res;
	}

	//查询出某一类型问题
	@PostMapping("/queryQuestionByType")
	public PageListRes queryQuestionByType(QueryVo qv) {
		PageListRes res = null;
		try {
			res = questionServiceimpl.queryQuestionByType(qv);
		}catch (Exception e) {
			e.printStackTrace();
			res = new PageListRes();
		}
		return res;
	}

    //生成试卷
	@PostMapping("/selectQuestion")
	public boolean selectQuestion(@RequestParam("username")String username,@RequestParam("tids[]")Integer[] tids,@RequestParam("questionTypeNumbers[]")Integer[] questionTypeNumbers) {
		List<Question> randomQuestionList = new ArrayList<Question>();
		try {
			for(int x=0;x<questionTypeNumbers.length;x++) {
				if(questionTypeNumbers[x]>0) {
					//获取改数量对应的题型
					int type = tids[x];//对应的题型id
					//查询出数据库所有的题型，包括其他用户提交的试题
					List<Question> questionList = questionServiceimpl.findAllQuestionByType(type);
					//防止为空
					if(questionList==null) {
						questionList = new ArrayList<>();
					}
					//随机选出questionTypeNumbers[x]条
					if(questionTypeNumbers[x]>=questionList.size()) {//如果超过了数据库中有的题型，那么将所有该类型的题目都给用户
						randomQuestionList.addAll(questionList);
						//randomQuestionMap.put(username, randomQuestionList);
					}else {
						//随机取出x条
						//这里取出的是题目的索引
						List<Integer> randomNumberList = NumberUtils.getRandomNumberList(questionList.size(), questionTypeNumbers[x]);
						List<Question> realRandomQuestionList = new ArrayList<Question>();
						for (int i = 0; i < randomNumberList.size(); i++) {
							int index = randomNumberList.get(i);
							Question vo = questionList.get(index);
							realRandomQuestionList.add(vo);
						}
						randomQuestionList.addAll(realRandomQuestionList);
					}//else结束 不需要全部
				}//用户需要这个题型的题
			}//for循环  一个题型的结束
			randomQuestionMap.put(username, randomQuestionList);//将用户的试卷存储进来
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
    //返回试卷结果
    @PostMapping("/getRandomTestPageByName")
    public ReturnRes getRandomTestPageByName(@RequestParam("username")String username){
    	System.out.println("------------------------");
    	ReturnRes res = new ReturnRes();
    	try {
    		res.setMsg("生成成功");
    		res.setSuccess(true);
    		res.setData(randomQuestionMap.get(username));
    	}catch (Exception e) {
    		e.printStackTrace();
    		res.setSuccess(false);
			res.setMsg("系统错误，请重试");
		}
    	return res;
    }

}
