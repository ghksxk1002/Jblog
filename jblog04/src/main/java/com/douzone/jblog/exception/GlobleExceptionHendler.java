package com.douzone.jblog.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.douzone.jblog.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobleExceptionHendler {
	private static final Log LOGGER = LogFactory.getLog(GlobleExceptionHendler.class);
	
	@ExceptionHandler(Exception.class)
	public void HandlerException(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception{
		
		// 1. 로깅
		// 버퍼를 가지고있는
		// String 이기 때문에  jsp로 보낸다
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOGGER.error(errors.toString());
	
		
		// 2. 요청 구분
		// 만약,JSON 요청인 경우, request header의 Accept에 application/json
		// 만약,HTMP 요청인 경우, request header의 Accept에 text/html
		
		// 헤더의 내용 뽑기
		String accept = request.getHeader("accept");
		// 페터매칭 : 정규식을 써줘야한다다".*"
		// application/json 이 문자열이 포함된
		if(accept.matches(".*application/json.*")) {
			//3. JSON 응답
			JsonResult result = JsonResult.fail(errors.toString());
			// 젝슨라이브러리를 직덥 사용해서 제이슨 생성 
			String jsonString = new ObjectMapper().writeValueAsString(result);
		
			// SC_OK : 500 에러
			response.setStatus(HttpServletResponse.SC_OK);
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
			
		} else {
			// 4. 사과페이지(사과 페이지로 가면 정상적으로 종료것이다), HTML 응답
			request.setAttribute("exception", errors.toString());
			request
			.getRequestDispatcher("/WEB-INF/views/error/exception.jsp")
			.forward(request, response);
			
		}
		
		
		
	
	}
}
