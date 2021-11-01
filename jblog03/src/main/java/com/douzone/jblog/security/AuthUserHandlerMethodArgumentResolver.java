package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.jblog.vo.UserVo;



public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {

		// 네이티브에 있는 리퀘스트 받아서 케스팅 
		HttpServletRequest request =(HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		// @AthUser가 붙어있지 않음
		if(authUser == null) {
			return false;
		}
		
		// 타입체크
		//파라미터 타입 가져와서 스트링으로 바꾸고
		//파라미터가 UserVo 타입이 아님
		if(parameter.getParameterType().equals(UserVo.class)==false) {
			return false;
		}
		
		return true;
	}

}
