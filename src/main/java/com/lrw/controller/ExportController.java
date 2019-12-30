package com.lrw.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Cell;
import com.lrw.service.QuestionService;
import com.lrw.util.ReturnRes;
import com.lrw.util.WriteDoc;
import com.lrw.vo.Question;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/export")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ExportController {

	@Autowired
	private QuestionService questionServiceimpl;
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
            int len = sheet.getLastRowNum();//获取这个sheet表单的行数
            System.out.println(len);
            for(int x=1;x<len;x++) {
            	Row row = sheet.getRow(1);//获取一行
            	System.out.println(row.getCell(0).getStringCellValue());
            }
			//List <Question> list =new ArrayList<Question>();
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

}
