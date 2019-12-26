package com.lrw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.QuestionBankService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.QuestionBank;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/questionBank")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class QuestionBankController {

	@Autowired
	private QuestionBankService QuestionBankServiceImpl;
	
	
	@ApiOperation("查询出每个人创建的题库")
	@PostMapping("/quereAllQuestionBank")
	public PageListRes quereAllQuestionBankByUsername(QueryVo qv) {
		PageListRes res = null;
  		try {
  			res = QuestionBankServiceImpl.findAllQuestionBankByUsername(qv);
  		}catch (Exception e) {
  			e.printStackTrace();
			res = new PageListRes();
		}
  		return res;
	}
	
	@ApiOperation("创建题库")
	@PostMapping("/addQuestionBank")
    public ReturnRes addQuestionBank(QuestionBank questionBank) {
		ReturnRes res = new ReturnRes();
		try {
			QuestionBankServiceImpl.addQuestionBank(questionBank);
			res.setMsg("创建成功");
		}catch (Exception e) {
			e.printStackTrace();
			res.setMsg("创建失败，请稍后再试");
		}
		return res;
	}
	
	

    @ApiOperation("改变题库的名称")
    @PostMapping("/changeQuestionBankName")
	public ReturnRes changeQuestionBankName(@RequestParam("qbid")@Nullable Integer qbid,@RequestParam("qbname")@Nullable String qbname) {
		ReturnRes res = new ReturnRes();
		try {
			QuestionBankServiceImpl.changeQuestionBankName(qbid,qbname);
			res.setMsg("修改成功");
			res.setSuccess(true);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res.setMsg("修改失败，系统异常，请稍后再试");
			res.setSuccess(false);
		}
		return res;
	}
    
    @ApiOperation("改变题库的名称")
    @PostMapping("/changeQuestionBankRemarks")
	public ReturnRes changeQuestionBankRemarks(@RequestParam("qbid")@Nullable Integer qbid,@RequestParam("remarks")@Nullable String remarks) {
		ReturnRes res = new ReturnRes();
		try {
			QuestionBankServiceImpl.changeQuestionBankRemarks(qbid,remarks);
			res.setMsg("修改成功");
			res.setSuccess(true);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res.setMsg("修改失败，系统异常，请稍后再试");
			res.setSuccess(false);
		}
		return res;
	}
    
    
    
    
    @ApiOperation("用户： 查询出所有可用的题库")
    @GetMapping("/queryAllEnableQB")
    public List<QuestionBank> queryAllEnableQB(@RequestParam("username")@Nullable String username){
    	List<QuestionBank> listAllEnableQT = QuestionBankServiceImpl.queryAllEnableQB(username);
    	return listAllEnableQT;
    }
    
    @PostMapping("/changeQuestionBankToNoSee")
    public ReturnRes changeQuestionBankToNoSeeByQbid(@RequestParam("qbid")@Nullable Integer qbid) {
    	ReturnRes res = new ReturnRes();
    	try {
			QuestionBankServiceImpl.changeQuestionBankToNoSeeByQbid(qbid);
			res.setMsg("删除成功，如果是误操作请联系管理员");
			res.setSuccess(true);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res.setMsg("删除失败，系统异常，请稍后再试");
			res.setSuccess(false);
		}
    	return res;
    }
    
	
}
