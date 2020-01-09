package com.lrw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lrw.util.QueryVo;
import com.lrw.vo.Kpoint;

@Mapper
public interface KpointMapper {

	List<Kpoint> findAllKpointByUsername(QueryVo qv);

	void addKpoint(Kpoint kpoint);

	Kpoint findKpointBykid(Integer kid);

	void changeKpointName(Integer kid, String kname);

	void changeKpointExplain(Integer kid, String kpointexplain);

	List<Kpoint> findAllPoint(String username);

	Kpoint isRepeateKp(String kpoint, String username);

}
