package com.douzone.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;



public class BlogInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		
		String id = "";
		int pathid = request.getServletPath().indexOf("/", 0)+1;
		int pathidend = request.getServletPath().indexOf("/", 1);
		System.out.println(id);
		if(pathidend < 0) {
			id = request.getServletPath().substring(pathid);
		} else {
			id= request.getServletPath().substring(pathid,pathidend);
		}
		
		BlogVo blogVo = (BlogVo)request.getServletContext().getAttribute("blogVo");
		System.out.println(blogVo);
		
		if(blogVo == null || id != blogVo.getId()) {
			blogVo = blogService.getBlog(id);
			request.getServletContext().setAttribute("blogVo", blogVo);
			
		}
		
		
		
		//System.out.println(blogVo);

		return true;
	}
}
