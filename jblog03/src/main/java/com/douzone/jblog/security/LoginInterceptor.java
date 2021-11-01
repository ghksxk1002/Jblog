package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;


public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("[LoginInterceptor] 들어왔음");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// new 를 해버리면 스프링이 만든것이 아니라 내가 만든것이기 때문에 스픠링에서 제어할수 없서
		// 유저 레파지토리가 null 이된다1
		UserVo authUser = userService.getUser(id, password);
		if(authUser == null) {
			request.setAttribute("result", "fail");
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				   .forward(request, response);
			System.out.println("[LoginInterceptor] 세션에 authUser 없어서 팅겼음");
			return false;
		}
		
		// session 처리
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		System.out.println("[LoginInterceptor] 세션에 authUser 저장했음");
		
		return false;
	}

}
