package com.lrw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.QuestionService;
import com.lrw.service.QuestionTypeService;
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
    
    
    @GetMapping("/selectQuestion")
    public PageListRes selectQuestion() {
    	//随机获取10道选择题
    	//选择题的数量
    	int selectQuestionCount = questionServiceimpl.findSelectQuestion();
    	//在这些选择题中随机选择10道
    	
    	//获取5道填空题
    	//获取2道设计题
    	
    	return null;
    }
    
	
    
	
	
	
	
	
}
