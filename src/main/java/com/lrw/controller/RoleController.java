package com.lrw.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrw.service.RoleService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.util.ReturnRes;
import com.lrw.vo.Role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/role")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class RoleController {
    @Autowired
	private RoleService roleServiceImpl;
	
    @ApiOperation("获取所有的角色")
	@GetMapping("/queryAllRole")
	public PageListRes queryAllUser(QueryVo qv){
		PageListRes queryRole =null;
		try {
			queryRole= roleServiceImpl.findAllRole(qv);
		}catch (Exception e) {
			queryRole = new PageListRes();
			e.printStackTrace();
		}
		return queryRole;
	} 
	
    @ApiOperation("查询出全部角色和用户已有角色，为已有角色打上标记")
	@PostMapping("/queryRoleById")
	public List<Role> queryRoleById(Integer id){
		//查出所有的角色
		List<Role> allRole = roleServiceImpl.findAllRolePlus();
		//用户具有的角色
		List<Role> userRole = roleServiceImpl.queryRoleById(id);
		for(int x=0;x<allRole.size();x++) {
			for(int y=0;y<userRole.size();y++) {
				if(allRole.get(x).getRid().equals(userRole.get(y).getRid())) {
					allRole.get(x).setChecked(true);
				}
			}
		}
		return allRole;
	}
	
    @ApiOperation("根据用户的id修改用户权限，操作的是中间表user_role")
	@PostMapping("/updateUserRoleById")
	public ReturnRes updateUserRoleById(@RequestParam("id")Integer id, @RequestParam("roleId")Integer[] roleId) {
		ReturnRes res = new ReturnRes();
		if(roleId.length==0) {
			res.setSuccess(false);
			res.setMsg("操作失败，用户至少拥有学生权限，如果不想赋予任何角色请封禁用户");
			return res;
		}else if(null==id) {
			res.setSuccess(false);
			res.setMsg("错误，错误原因：用户id为空，请稍后再试");
			return res;
		}else {
			try {
				//先删除用户已有的权限
				roleServiceImpl.deleteUserRolesById(id);
				//增加角色
				for(int x=0;x<roleId.length;x++) {
					roleServiceImpl.addUserRoles(id,roleId[x]);
				}
				res.setMsg("操作成功");
				res.setSuccess(true);
				return res;
			}catch (Exception e) {
				e.printStackTrace();
				res.setSuccess(false);
				res.setMsg("错误，错误原因：修改角色错误");
				return res;
			}
		}
		
		
	}
	
    @ApiOperation("修改角色对应的菜单")
    @PostMapping("/changeRoleMenus")
	public ReturnRes changeRoleMenus(@RequestParam("rid")Integer rid, @RequestParam("menuId")Integer[] menuId) {
    	ReturnRes res = new ReturnRes();
    	if(null==rid||null==menuId) {
    		res.setMsg("系统出错，请稍后再试");
		}else {
			try {
				//先删除已有的菜单，操作的都是role_menu表
				roleServiceImpl.deleteMenuByRid(rid);
				//配置新的菜单
				for(int x =0;x<menuId.length;x++) {
					roleServiceImpl.addMenuToRole(rid,menuId[x]);
				}
				res.setMsg("修改成功");
				res.setSuccess(true);
			}catch (Exception e) {
				e.printStackTrace();
				res.setMsg("系统异常，请稍后再试");
				res.setSuccess(false);
			}
		}
    	return res;
	}
    @ApiOperation("增加角色")
    @PostMapping("/addRole")
    public ReturnRes addRole(@RequestParam("rname") @NotNull String rname) {
    	ReturnRes res = new ReturnRes();
    	boolean flag = roleServiceImpl.findRoleByName(rname);
    	if(flag) {//为空
    		try {
    			roleServiceImpl.addRole(rname);
    			res.setMsg("增加成功");
    			res.setSuccess(true);
    		}catch(Exception e){
    			res.setMsg("增加失败,系统异常");
    			res.setSuccess(false);
    		}
    	}else {//存在该角色
    		res.setMsg("增加失败，已存在该角色");
    		res.setSuccess(false);
    	}
    	return res;
    }

}
