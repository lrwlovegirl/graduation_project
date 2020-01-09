package com.lrw.service;

import java.util.List;

import com.lrw.util.PageListRes;
import com.lrw.util.QueryVo;
import com.lrw.vo.Kpoint;

public interface KpointService {

	PageListRes findAllKpointByUsername(QueryVo qv);

	void addKpoint(Kpoint kpoint);

	Kpoint findKpointByTid(Integer kid);

	void changeKpointName(Integer kid, String kname);

	void changeKpointExplain(Integer kid, String kpointexplain);

	List<Kpoint> findAllPoint(String username);

	boolean isRepeateKp(String kpoint, String username);
	
	Kpoint findKpointByKnameAndCreateUser(String kpoint, String username);

}
