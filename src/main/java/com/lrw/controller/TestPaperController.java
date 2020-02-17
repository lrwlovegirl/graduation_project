package com.lrw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.QuestionService;
import com.lrw.service.TestPageService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.Question;
import com.lrw.vo.QuestionTidAndType;
import com.lrw.vo.TestPage;

@RestController
@RequestMapping("/testPaper")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Transactional(rollbackFor = Exception.class)//出错后进行回滚
public class TestPaperController {
    @Autowired
	private TestPageService testPageServiceImpl;
    @Autowired
    private QuestionService questionServiceImpl;
    //存储uuid的
    private Map<String,TestPage> testPageMap = new ConcurrentHashMap<>() ;

    @PostMapping("/queryAllTestPageByUserName")
	public PageListRes queryAllTestPageByUserName(QueryVo queryVo) {
		PageListRes res = testPageServiceImpl.queryAllTestPageByUserName(queryVo);
		return res;
	}
	//questionTypeNumbers   题型的数量
	//questionScore         每种题型的分值
	//tids                  对用题型的id
	@PostMapping("/addRandomTestPage")
	public ReturnRes addRandomTestPage(@RequestParam("topic")String topic ,@RequestParam("username")String username,
			@RequestParam("tids[]")Integer[] tids,
			@RequestParam("questionTypeNumbers[]")Integer[] questionTypeNumbers,
			@RequestParam("questionScore[]")Integer[] questionScore) {
		TestPage testPage = new TestPage();
		ReturnRes res =new ReturnRes();
		try {
			String uuid = UUID.randomUUID().toString().substring(0, 10);
			testPage.setTopic(topic);
			testPage.setCreatetype(0);
			testPage.setCreateuser(username);
			testPage.setTpid(uuid);
			testPageServiceImpl.addRandTestPage(testPage);//将试卷保存起来
			testPageServiceImpl.addRandomTestPage(uuid, questionTypeNumbers, questionScore, tids);//将试卷内容保存
			res.setSuccess(true);
			res.setMsg("创建成功，编号为："+uuid+" ,请点击查看");
		}catch (Exception e) {
			res.setMsg("创建失败，请稍后再试");
			res.setSuccess(false);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚 
		}
		return res;
	}
	@PostMapping("/isRepeatTopicName")
	public ReturnRes isRepeatTopicName(@RequestParam("topic")String topic,@RequestParam("username")String username) {
		ReturnRes res = new ReturnRes();
		if(username == null) {
			res.setMsg("系统错误，请重新登录");
		}else {
			//有返回false ，没有返回true
			boolean flag = testPageServiceImpl.isRepeatTopicName(topic,username);
			if(flag) {
				res.setSuccess(false);
			}else {
				res.setMsg("存在相同的试卷名称哦，换一个呗");
				res.setSuccess(true);
			}
		}
		return res;
	}
	@PostMapping("/seeTestPaperDetailsByTpid")
    public TestPage seeTestPaperDetailsByTpid(@RequestParam("tpid")String tpid) {
    	 return testPageServiceImpl.seeTestPaperDetailsByTpid(tpid);
    }
	
	@PostMapping("/deleteTestPaperBytpid")
	public ReturnRes deleteTestPageByTpid(@RequestParam("tpid")String tpid) {
		ReturnRes res = new ReturnRes();
		try {
			testPageServiceImpl.deleteTestPaperByTpid(tpid);
			res.setMsg("删除成功");
			res.setSuccess(true);
		}catch (Exception e) {
		  e.printStackTrace();
          res.setSuccess(false);
          res.setMsg("系统异常，请稍后再试");
          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚 
		}
		return res;
	}
	
	//用户指定一张试卷的题型，按照将本类所有的题型返给他
	@PostMapping("/artificalQt")
    public String artificalTestPaper(@RequestParam("qt[]")Integer[] qt,@RequestParam("qts[]")int[] qts,@RequestParam("username")String username,@RequestParam("topic")String topic) {
        TestPage testPage  = new TestPage();
        String uuid = UUID.randomUUID().toString().substring(0,10);
		try {
            testPage = testPageServiceImpl.artificalTestPaper(qt,username,topic,uuid);
            testPage.setQts(qts);
            testPageMap.put(uuid, testPage);
        }catch (Exception e) {
           e.printStackTrace();
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚 
        }
    	return uuid;
    }
	@PostMapping("/displayTestPageFromMap")
	public TestPage displayTestPageFromMap(@RequestParam("uuid")String uuid) {
		System.out.println(uuid);
		return testPageMap.get(uuid);
	}
	
	//为已经存在的试卷重新设置具体题目
	@PostMapping("/addArtificalTpQuestions")
	public ReturnRes addArtificalTpQuestions(@RequestParam("uuid")String uuid,@RequestParam("qtId[]") @NotNull Integer[] qtId ) {
		TestPage testPage = testPageMap.get(uuid);
		ReturnRes res = new ReturnRes();
		if(testPage==null) {
			res.setSuccess(false);
		}else {
			//遇到的问题：题目类型和题型分值对应不上-->第一种题型和第一个分值对应
		  List<Question> questionList = questionServiceImpl.selectQuestionByQids(qtId);
		  if(questionList==null||questionList.size()==0) {
			  res.setSuccess(false);
		  }else {
			  boolean flag =testPageServiceImpl.saveArtificalTpQuestionList(uuid, questionList, testPage);
			  if(flag) {//保存成功就移除map中的试卷
				  testPageMap.remove(uuid); 
				  res.setSuccess(true);
			  }else {
				  res.setSuccess(false);
			  }
		  }
		}
		return res;
	}
	
	
	
	
}
