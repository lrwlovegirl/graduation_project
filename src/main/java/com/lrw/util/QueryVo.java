package com.lrw.util;

import java.io.Serializable;

/**
 * 用于接收前端发送的页码和查询数据的条数
 * 还有模糊查询的关键字
 */
public class QueryVo  implements Serializable{
    private int page;
    private int limit;
    private String keyword;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "QueryVo [page=" + page + ", limit=" + limit + ", keyword=" + keyword + "]";
	}
	
    
}
