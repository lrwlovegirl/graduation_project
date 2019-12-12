package com.lrw.vo;

import java.util.List;

import lombok.Data;

@Data
public class Result {
  
	private List<Menu> contentManagement;
	private List<Menu> memberCenter;
	private List<Menu> seraphApi;
}
