package com.lrw.vo;

import java.util.List;

import lombok.Data;

@Data
public class Menu {
	private String title;
	private String icon;
	private String href;
	private boolean spread=false;
	private List<Menu> children;
	

}
