package com.ssm.config;

import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jndi.JndiTemplate;

import com.ssm.global.alias.SsmEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
public class DataSourceConfig extends AbstractJdbcConfiguration implements EnvironmentAware {

	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		System.out.println("✅ spring.Environment 시작" + environment);

		this.env = environment;
	}

    /**
     * 프로퍼티 변수로 값을 받아와 datasource 설정하여 bean 등록
     * @return DataSource
     * @throws
     * @throws Exception
     * 
     */    
    @Primary
    @Bean(name = "dataSourceMaria")
    DataSource dataSourceMaria() throws Exception {
		DataSource dataSource = null;
		System.out.println("✅ spring.datasource 시작");

//		if("jndi".equals(env.getProperty("spring.datasource.type"))) {
//			dataSource = (DataSource) new JndiTemplate().lookup("spring.datasource.jndi-name");
//		}else if("basic".equals(env.getProperty("spring.datasource.type"))) {
			HikariConfig config = new HikariConfig();
			System.out.println("✅ spring.datasource.url: " + env.getProperty("spring.datasource.url"));
			System.out.println("✅ spring.datasource.username: " + env.getProperty("spring.datasource.username"));
			System.out.println("✅ spring.datasource.password: " + env.getProperty("spring.datasource.password"));
			System.out.println("✅ spring.datasource.type: " + env.getProperty("spring.datasource.type"));
			config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
			config.setJdbcUrl(env.getProperty("spring.datasource.url"));
			config.setUsername(env.getProperty("spring.datasource.username"));
			config.setPassword(env.getProperty("spring.datasource.password"));
			config.setMaximumPoolSize(10);
			config.setAutoCommit(true);
			dataSource = new HikariDataSource(config);
//		}
		
		return dataSource;
	}
	
	/**
	 * Mybatis Session Factory 설정
	 * @return DataSource
	 * @throws
	 * @throws Exception
	 * 
	 */	
	@Primary
	@Bean(name = "sqlSessionMaria")
	SqlSessionFactoryBean SqlSessionFactoryBeanMaria(@Qualifier("dataSourceMaria") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		
		configuration.setCacheEnabled(true);
		configuration.setLazyLoadingEnabled(true);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setCallSettersOnNulls(true);
		configuration.setDefaultExecutorType(ExecutorType.REUSE);
		sessionFactoryBean.setConfiguration(configuration);
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*.xml"));
		sessionFactoryBean.setTypeAliasesPackage("com.ssm.dao");
		
		Reflections reflections = new Reflections("com.ssm");
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(SsmEntity.class);
		
		sessionFactoryBean.setTypeAliases(classes.toArray(new Class<?>[0]));
		
		return sessionFactoryBean;
	}

}
