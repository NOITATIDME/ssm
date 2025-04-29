package com.ssm.sample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.sample.SampleDao;
import com.ssm.sample.service.SampleService;

@Service
public class SampleServiceImpl implements SampleService {
	
	@Autowired
	private SampleDao sampleDao;
	
	@Override
	public int selectId() {
		return sampleDao.selectId();
	}
}
