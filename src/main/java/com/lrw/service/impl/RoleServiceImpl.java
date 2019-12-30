package com.lrw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrw.mapper.RoleMapper;
import com.lrw.service.RoleService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Role;
import com.lrw.vo.User;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public PageListRes findAllRole(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<Role> list = this.roleMapper.findAllRole(qv);
		PageInfo<Role> page = new PageInfo<Role>(list);
		res.setData(page.getList());
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}


	
	@Override
	public List<Role> queryRoleById(Integer id) {
		// TODO Auto-generated method stub
		return roleMapper.queryRoleById(id);
	}



	@Override
	public List<Role> findAllRolePlus() {
		// TODO Auto-generated method stub
		return roleMapper.findAllRolePlus();
	}



	@Override
	public void deleteUserRolesById(Integer id) {
		roleMapper.deleteUserRolesById(id);
	}



	@Override
	public void addUserRoles(Integer id, Integer rid) {
		roleMapper.addUserRoles(id,rid);
	}



	@Override
	public void deleteMenuByRid(Integer rid) {
		roleMapper.deleteMenuByRid(rid);
	}



	@Override
	public void addMenuToRole(Integer rid, Integer mid) {
		roleMapper.addMenuToRole(rid,mid);
	}

}
