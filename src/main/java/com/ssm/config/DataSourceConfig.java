package com.ssm.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

@Configuration
@PropertySource("classpath:application-${spring.profiles.active}.yml")
public class DataSourceConfig extends AbstractJdbcConfiguration implements EnvironmentAware {

	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Primary
	@Bean(name = "dataSourceMaria")
	public DataSource dataSourceMysql() throws Exception {
		DataSource dataSource = null;

		return dataSource;
	}

	@Primary
	@Bean(name = "sqlSessionMaria")
	public SqlSessionFactoryBean SqlSessionFactoryBeanMysql(@Qualifier("dataSourceMaria") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		return sqlSessionFactoryBean;
	}

}
