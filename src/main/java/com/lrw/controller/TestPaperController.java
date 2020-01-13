package com.lrw.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lrw.service.TestPageService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.TestPage;

@RestController
@RequestMapping("/testPaper")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Transactional(rollbackFor = Exception.class)//出错后进行回滚
public class TestPaperController {
    @Autowired
	private TestPageService testPageServiceImpl;
	
	
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
		System.out.println("#############");
		TestPage testPage = new TestPage();
		ReturnRes res =new ReturnRes();
		try {
			String uuid = UUID.randomUUID().toString().substring(0, 10);
			testPage.setTopic(topic);
			testPage.setCreateuser(username);
			testPage.setTpid(uuid);
			testPageServiceImpl.addRandTestPage(testPage);
			res.setSuccess(true);
			res.setMsg("创建成功，编号为："+uuid+" ,请点击查看");
		}catch (Exception e) {
			e.printStackTrace();
			res.setMsg("创建失败，请稍后再试");
			res.setSuccess(false);
		}
		return res;
	}
	
	
	
}
