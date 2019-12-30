package com.lrw.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//分页的工具类，用于返回总条数和查询的数据
public class PageListRes implements Serializable{
    private String status="200";
    private String code="0";
    private String message="";
    private Long total; 
    private Long number;
    private Long count;
    private List<?> data=new ArrayList();
    private Object obj;
    
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> rows) {
		this.data = rows;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
   
    
}
