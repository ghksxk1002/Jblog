package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

// 여기가 DB 설정을 자바 컴피그로 한다
// 컨테이너에 데이터 소르를 만들어준다
/*
 * 		<!-- Connection Pool DataSource : db 와의 연결을 담당 -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
		<property name="url" value="jdbc:mysql://127.0.0.1:3306/webdb?characterEncoding=utf8" />
		<property name="username" value="webdb" />
		<property name="password" value="webdb" />
	</bean>
	요걸 만들어주는 작업
 */  
@Configuration
@PropertySource("classpath:com/douzone/jblog/config/app/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.diriverCalssName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.userName"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize",Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));
		
		return dataSource;
	}
}
