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

import com.lrw.service.KpointService;
import com.lrw.service.QuestionService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.Kpoint;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/kpoint")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class KpointController {

	@Autowired
	private KpointService kpointServiceImpl;
	
	@Autowired
	private QuestionService questionServiceimpl;
	
	@ApiOperation("查： 查询出每个人创建的知识点")
	@PostMapping("/quereAllKpoint")
	public PageListRes quereAllKpointByUsername(QueryVo qv) {
		PageListRes res = null;
  		try {
  			res = kpointServiceImpl.findAllKpointByUsername(qv);
  		}catch (Exception e) {
  			e.printStackTrace();
			res = new PageListRes();
		}
  		return res;
	}
	
	@ApiOperation("增： 创建知识点")
	@PostMapping("/addKpoint")
    public ReturnRes addKpoint(Kpoint kpoint) {
		System.out.println("执行了");
		ReturnRes res = new ReturnRes();
		try {
			kpointServiceImpl.addKpoint(kpoint);
			res.setMsg("创建成功");
		}catch (Exception e) {
			res.setMsg("创建失败，请稍后再试");
		}
		return res;
	}
	

    @ApiOperation("改变知识点的名称")
    @PostMapping("/changeKpointName")
	public ReturnRes changeKpointName(@RequestParam("kid")@Nullable Integer kid,@RequestParam("kname")@Nullable String kname) {
		ReturnRes res = new ReturnRes();
		try {
			kpointServiceImpl.changeKpointName(kid,kname);
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

    
    @ApiOperation("改变知识点的讲解部分")
    @PostMapping("/changeKpointExplain")
	public ReturnRes changeKpointExplain(@RequestParam("kid")@Nullable Integer kid,@RequestParam("kpointexplain")@Nullable String kpointexplain) {
		ReturnRes res = new ReturnRes();
		try {
			kpointServiceImpl.changeKpointExplain(kid,kpointexplain);
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
    
    @ApiOperation("查询出该用户所有的知识点,不进行分页，该方法用在题目的增加页面")
	@PostMapping("/findAllKpoint")
	public List<Kpoint> findAllKpoint(String username){
		List<Kpoint> kpointList = kpointServiceImpl.findAllPoint(username);
		return kpointList;
	}
    
    
    @ApiOperation("根据传过来的题号，查询出该题号的知识点，并将其标记，返回所有的知识点")
    @PostMapping("/findAllKpointByQid")
    public List<Kpoint> findAllKpoint(@RequestParam("qid")@Nullable Integer qid,@RequestParam("username")@Nullable String username ){
    	String kname = questionServiceimpl.findQuestionByQid(qid).getAbout();
    	List<Kpoint> kpointList = kpointServiceImpl.findAllPoint(username);//查询出所有的知识点
    	for(int x = 0;x<kpointList.size();x++) {
    		if(kpointList.get(x).getKname().equals(kname)) {
    			kpointList.get(x).setMark(true);
    		}
    	}
    	return kpointList;
    }
	
}
