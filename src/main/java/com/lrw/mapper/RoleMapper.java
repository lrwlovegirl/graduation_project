package com.lrw.mapper;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.util.QueryVo;
import com.lrw.vo.Role;

@Mapper
public interface RoleMapper {
	List<Role> findAllRole(QueryVo qv);

	List<Role> queryRoleById(Integer id);

	List<Role> findAllRolePlus();

	void deleteUserRolesById(Integer id);

	void addUserRoles(Integer id, Integer rid);

	void deleteMenuByRid(Integer rid);

	void addMenuToRole(Integer rid, Integer mid);

	Role findRoleByName(@NotNull String rname);

	void addRole(@NotNull String rname);
}
