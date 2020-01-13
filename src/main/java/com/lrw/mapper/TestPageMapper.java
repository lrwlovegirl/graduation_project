package com.lrw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.vo.TestPage;

@Mapper
public interface TestPageMapper {

	List<TestPage> queryAllTestPageByUserName(String username);

	void addRandTestPage(TestPage testPage);
	
	
}
