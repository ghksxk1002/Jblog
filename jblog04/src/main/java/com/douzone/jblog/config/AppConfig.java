package com.douzone.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.config.app.DBConfig;
import com.douzone.config.app.MyBaitsConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.douzone.jblog.service","com.douzone.jblog.repository","com.douzone.jblog.aspect"})
@Import({DBConfig.class, MyBaitsConfig.class})
public class AppConfig {
	// 야기는 설정들만 땡겨오는곳
}
