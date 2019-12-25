package com.lrw.util;

import java.util.List;

import com.lrw.vo.Role;

import lombok.Data;

@Data
public class ReturnRoleList {

	private List<Role> allRoleList;
	private List<Role> userRoleList;
	
}
