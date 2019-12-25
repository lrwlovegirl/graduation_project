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

import com.lrw.service.QuestionTypeService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.QuestionType;

import io.swagger.annotations.ApiOperation;

//题型管理
@RestController
@RequestMapping("/questionType")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class QuestionTypeController {

	@Autowired
	private QuestionTypeService questionTypeServiceImpl;
	
	
	@ApiOperation("查询出每个人创建的题类型")
	@PostMapping("/quereAllQuestionType")
	public PageListRes quereAllQuestionTypeByUsername(QueryVo qv) {
		PageListRes res = null;
  		try {
  			res = questionTypeServiceImpl.findAllQuestionTypeByUsername(qv);
  		}catch (Exception e) {
  			e.printStackTrace();
			res = new PageListRes();
		}
  		return res;
	}
	
	@ApiOperation("创建题型")
	@PostMapping("/addQuestionType")
    public ReturnRes addQuestionType(QuestionType questioType) {
		ReturnRes res = new ReturnRes();
		try {
			questionTypeServiceImpl.addQuestionType(questioType);
			res.setMsg("创建成功");
		}catch (Exception e) {
			res.setMsg("创建失败，请稍后再试");
		}
		return res;
	}
	
	@ApiOperation("改变题型的状态，可用-->不可用，不可用-->可用")
	@PostMapping("/changeQuestionTypeStatus")
	public ReturnRes changeQuestionTypeStatus(@RequestParam("tid")@Nullable Integer tid) {
		ReturnRes res = new ReturnRes();
		QuestionType questionType = questionTypeServiceImpl.findQuestionTypeByTid(tid);
		try {
			if(questionType.getStatus()==0) {
				//变为不可用 1
				questionTypeServiceImpl.changeQTStatusToUnable(tid);
			}else {
				//变为可用
				questionTypeServiceImpl.changeQTStatusToAble(tid);
			}
			res.setMsg("更改成功");
		}catch (Exception e) {
			res.setMsg("系统异常，请稍后再试");
		}
		return res;
	}

    @ApiOperation("改变题型的名称")
    @PostMapping("/changeQuestionTypeName")
	public ReturnRes changeQuestionTypeName(@RequestParam("tid")@Nullable Integer tid,@RequestParam("name")@Nullable String name) {
		ReturnRes res = new ReturnRes();
		try {
			questionTypeServiceImpl.changeQuestionTypeName(tid,name);
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

    
    @ApiOperation("查询出所有可用的题型")
    @GetMapping("/queryAllEnableQT")
    public List<QuestionType> queryAllEnableQT(){
    	List<QuestionType> listAllEnableQT = questionTypeServiceImpl.queryAllEnableQT();
    	return listAllEnableQT;
    }

}
