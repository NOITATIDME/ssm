package com.ssm.dao.sample;

import org.springframework.stereotype.Repository;

import com.ssm.dao.sample.mapper.SampleMapper;
import com.ssm.global.dao.MariaAcstractMapper;

@Repository
public class SampleDao extends MariaAcstractMapper{
	public int selectId() {
		return getSqlSession().getMapper(SampleMapper.class).selectId();
	}
}
