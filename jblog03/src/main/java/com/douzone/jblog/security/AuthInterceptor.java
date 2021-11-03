package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.vo.UserVo;


public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 1. handler 종류 확인
		if (handler instanceof HandlerMethod == false) {
			return true;
		}

		// 2. casting : handler Method로 케스팅을 해놓아야 한다
		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 3. HandlerMethod 의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		//System.out.println(auth);

		// 4. HandlerMethod 에 @Auth가 없으면 TYPE 에 붙어있는지 확인
		if (auth == null) {
			/* 오늘 과제 */
			// auth = handlerMethod.
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);

		}

		// 5. TYPE(class)과 Method에 @Auth가 적용이 안되어 있는 경우
		if (auth == null) {
			return true;
		}

		// 6. 인증체크 : @Auth가 적용이 되어 있기 때문에 인증(Authenfication) 여부 확인
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/user");
			return false;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user");
			return false;
		}

		// 7. 권한 체크를 위해서 @Auth의 role 가져오기("ID", "GUEST")
		String role = auth.role();

		// 8. @Auth의 role이 "USER" 인 경우, authUser의 role은 상관없다.
		if ("USER".equals(role)) {
			return true;
		}
		
		String userId = request.getServletPath();
		System.out.println("[userId]"+userId);
		
		if (userId.indexOf("/"+authUser.getId()+"/") == -1) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		// 옳은 관리자 권한
		// @Auth의 role: "ADMIN"
		// authUser의 role: "ADMIN"
		return true;
	}

}
