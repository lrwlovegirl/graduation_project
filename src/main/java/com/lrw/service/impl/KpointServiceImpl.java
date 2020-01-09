package com.lrw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lrw.mapper.KpointMapper;
import com.lrw.service.KpointService;
import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Kpoint;
import com.lrw.vo.QuestionType;
@Service
public class KpointServiceImpl implements KpointService {
    @Autowired
	private KpointMapper kpointMapper;
	
	
	@Override
	public PageListRes findAllKpointByUsername(QueryVo qv) {
		PageListRes res = new PageListRes();
		PageHelper.startPage(qv.getPage(),qv.getLimit());// （当前页，每页条数）
		List<Kpoint> list = this.kpointMapper.findAllKpointByUsername(qv);
		PageInfo<Kpoint> page = new PageInfo<Kpoint>(list);
		res.setData(page.getList());
		res.setTotal(page.getTotal());
		res.setNumber(page.getTotal());
		res.setCount(page.getTotal());
		return res;
	}

	@Override
	public void addKpoint(Kpoint kpoint) {
		kpointMapper.addKpoint(kpoint);
	}

	@Override
	public Kpoint findKpointByTid(Integer kid) {
		return kpointMapper.findKpointBykid(kid);
	}

	@Override
	public void changeKpointName(Integer kid, String kname) {
		kpointMapper.changeKpointName(kid,kname);
	}

	@Override
	public void changeKpointExplain(Integer kid, String kpointexplain) {
		kpointMapper.changeKpointExplain(kid,kpointexplain);
	}

	@Override
	public List<Kpoint> findAllPoint(String username) {
		// TODO Auto-generated method stub
		return kpointMapper.findAllPoint(username);
	}

	@Override
	public boolean isRepeateKp(String kpoint, String username) {
		// TODO Auto-generated method stub
		return null==kpointMapper.isRepeateKp(kpoint,username);
	}

	@Override
	public Kpoint findKpointByKnameAndCreateUser(String kpoint, String username) {
		// TODO Auto-generated method stub
		return kpointMapper.isRepeateKp(kpoint, username);
	}

}
