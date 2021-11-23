package com.douzone.config.web;


import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc // MVC 할때 필요한 메세지 컨버터, 부리졸버,인터셉터 인에이블
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	// 뷰리졸버
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	// 메세지 컨버터
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		
		
		stringHttpMessageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("text", "html", Charset.forName("UTF-8"))
						)
				);
		return stringHttpMessageConverter;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		
		Jackson2ObjectMapperBuilder builder 
				= new Jackson2ObjectMapperBuilder()
						.indentOutput(true)
						.dateFormat(new SimpleDateFormat("yyyy-mm-dd")); 
		MappingJackson2HttpMessageConverter MessageConverter 
		 		= new MappingJackson2HttpMessageConverter(builder.build());
		
		MessageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("application", "json", Charset.forName("UTF-8"))
						)
				);
		return MessageConverter;
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}
	
	

	// Default Servlet Hander
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
