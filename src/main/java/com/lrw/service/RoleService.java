package com.lrw.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Role;

public interface RoleService {

	PageListRes findAllRole(QueryVo qv);

	List<Role> queryRoleById(Integer id);
	
	List<Role> findAllRolePlus();

	void deleteUserRolesById(Integer id);

	void addUserRoles(Integer id,Integer rid);

	void deleteMenuByRid(Integer rid);

	void addMenuToRole(Integer rid, Integer integer);

	boolean findRoleByName(@NotNull String rname);

	void addRole(@NotNull String rname);

}
