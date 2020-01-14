package com.lrw.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lrw.service.KpointService;
import com.lrw.service.QuestionService;
import com.lrw.service.QuestionTypeService;
import com.lrw.service.TestPageService;
import com.lrw.util.ReturnRes;
import com.lrw.util.WriteDoc;
import com.lrw.vo.Question;
import com.lrw.vo.QuestionType;
import com.lrw.vo.TestPage;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/export")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ExportController {

	@Autowired
	private QuestionService questionServiceimpl;
	@Autowired
	private QuestionTypeService questionServiceImpl;
	@Autowired
	private KpointService kpointServiceImpl;
	@Autowired
	private TestPageService testPageServiceImpl;
	
	@ApiOperation("批量导出题目")
	@PostMapping("/exportQuestion")
	public ReturnRes exportQuestion(@RequestParam(value="qids[]") @NotNull Integer[] qids) {
		System.out.println(qids);
		ReturnRes res = new ReturnRes();
		if(null==qids||qids.length==0) {
			res.setMsg("导出失败，失败原因，传入qid为空");
			res.setSuccess(false);
			return res;
		}else {
			try {
				List<Question> questionList = new ArrayList();
				for(int x=0;x<qids.length;x++) {
					questionList.add(questionServiceimpl.findQuestionByQid(qids[x]));
				}		
				String fileName = new SimpleDateFormat("yyyyMMdd").
						format(new Date())+UUID.randomUUID().toString().substring(0,5)+"导出数据.docx";
				WriteDoc.createWord(questionList, fileName);
				res.setMsg("导出成功，导出文件在桌面上，导出文件名称为："+fileName);
				res.setSuccess(true);
			}catch (Exception e) {
				e.printStackTrace();
				res.setMsg("系统异常,稍后再试");
				res.setSuccess(false);
			}
			return res;
		}
	}

	@ApiOperation("批量导入")
	@PostMapping("/exportInQuestion")
	public ReturnRes exportInQuestion(@RequestParam(value = "file")MultipartFile file,String username) throws Exception {
		ReturnRes res = new ReturnRes();
		System.out.println(username);
		//上传的是excel文件
		if("application/vnd.ms-excel".equals(file.getContentType().toString())||
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(file.getContentType().toString())) {
			InputStream inputStream = file.getInputStream();
			Workbook wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheetAt(0);//获取第一个表单
			if(null == sheet) {
				res.setMsg("上传的文件错误，请检查");
				res.setSuccess(false);
				return res ;
			}
			int len = sheet.getLastRowNum();//获取这个sheet表单用户操作过的最后一行行号
			System.out.println("用户操作过的行数："+len);
			List<Question> questionList = new ArrayList();
			try {
				for(int x=1;x<=len;x++) {
					Row row = sheet.getRow(x);//获取一行
					if(row == null) {
						//                		res.setMsg("上传文件错误，请检查");
						//                		res.setSuccess(false);
						//                		return res;
						continue;
					}
					//获取题目
					String title = getCellValue(row.getCell(0)).toString();
					//获取题型
					String typename = getCellValue(row.getCell(1)).toString();
					//存在题型直接插入，不存在就先创建题型
					boolean repeateQt = questionServiceImpl.isRepeateQt(typename, username);
					if(repeateQt) {//不存在题目
						QuestionType  newQuestionType = new QuestionType();
						newQuestionType.setName(typename);
						newQuestionType.setCreateuser(username);
						newQuestionType.setStatus(0);
						questionServiceImpl.addQuestionType(newQuestionType);
					}
					//查询出该题型对应的编号--Question 的type
					Integer tid = questionServiceImpl.findQuestionTypeByNameAndCreateUser(typename,username);
					//具体要求; --Question 的content
					String content = getCellValue(row.getCell(2)).toString();
					//选项
					String optionA =getCellValue(row.getCell(3)).toString();
					String optionB =getCellValue(row.getCell(4)).toString();
					String optionC =getCellValue(row.getCell(5)).toString();
					String optionD =getCellValue(row.getCell(6)).toString();
					//答案
					String answer = getCellValue(row.getCell(7)).toString();
					//备注
					String analysis =getCellValue(row.getCell(8)).toString();
					//知识点
					//                   String kpoint = getCellValue(row.getCell(9)).toString();
					//                   if(null!=kpoint) {
					//                	   boolean repeateKp = kpointServiceImpl.isRepeateKp(kpoint,username);
					//                       if(repeateKp) {//是空
					//                    	Kpoint newKpoint = new Kpoint();
					//                    	newKpoint.setCreateuser(username);
					//                    	newKpoint.setKname(kpoint);
					//    					kpointServiceImpl.addKpoint(newKpoint);
					//                       }	
					//                       Kpoint kpointByName = kpointServiceImpl.findKpointByKnameAndCreateUser(kpoint,username);
					//                   }
					Question question = new Question();
					question.setTitle(title);
					question.setType(tid);
					question.setQuestiontype(typename);
					question.setAnswer(answer);
					question.setAnalysis(analysis);
					question.setContent(content);
					question.setOptionA(optionA);
					question.setOptionB(optionB);
					question.setOptionC(optionC);
					question.setOptionD(optionD);
					question.setPublisher(username);
					System.out.println(question);
					//保存到集合，一次性提交，出错也不至于有的提交了，有的没提交
					questionList.add(question);
					//questionServiceimpl.createQuestion(question);
				}
				if(questionList.size()>0) {
					questionServiceimpl.createQuestionList(questionList);
				}
				res.setMsg("解析成功,已为您上传到数据库，可在题目列表查看");
			}catch (NullPointerException e) {
				res.setMsg("您上传的文件某一行存在数据为空的情况，请检查");
				res.setSuccess(false);
				return res;
			}catch (Exception e) {
				e.printStackTrace();
				res.setMsg("系统异常，请稍后再试");
				res.setSuccess(false);
				return res;
			}
			return res;
		}else {
			res.setMsg("错误，上传不是excel文件，请检查");
			res.setSuccess(false);
			return res;
		}
	}
	/**
	 * 工具方法，判断每一个excel单元的内容的数据类型
	 * @param cell
	 * @return
	 */
	private  Object getCellValue(Cell cell){ 
		switch (cell.getCellType()) {
		case STRING:
			return cell.getRichStringCellValue().getString();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();//这里返回的Double类型
			}
		case BOOLEAN:
			return cell.getBooleanCellValue();
		case FORMULA:
			return cell.getCellFormula();
		}
		return cell;
	}

	@PostMapping("/exportTestPageByTpid")
	public ReturnRes exportTestPageByTpid(@RequestParam("tpid") String tpid) {
		ReturnRes res = new ReturnRes();
		try {
          TestPage testPage = testPageServiceImpl.seeTestPaperDetailsByTpid(tpid);
          if(testPage == null ) {testPage = new TestPage();}
          String name = ""+UUID.randomUUID().toString().substring(0, 5)+"试卷.doc";
          WriteDoc.exportTestPage(testPage.exportTestPage(), name);
          res.setSuccess(true);
          res.setMsg("导出成功，请在桌面查看:"+name+"文件");
		}catch(Exception e){
          e.printStackTrace();
          res.setMsg("系统异常，请稍后再试");
          res.setSuccess(false);
		}
		return res ;
	}

}
