package com.ssm.global.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import jakarta.annotation.Resource;

public class MariaAcstractMapper extends SqlSessionDaoSupport {

	@Override
	@Resource(name="sqlSessionMaria")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}
}
