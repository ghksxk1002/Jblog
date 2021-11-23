package com.douzone.jblog.dto;

public class JsonResult {
	private String result;/*"success"or "fail"*/
	private Object data;/*if success,set*/
	private String message;
	
	// 기본 생성자를 프라이빗으로 만들어서 참조를 막는다
	private JsonResult() {}
	
	private JsonResult(Object data) {
		result = "success";
		this.data = data;
		message = null;
	}
	

	private JsonResult(String message) {
		result = "fail";
		data = null;
		this.message = message;
	}
	
	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}
	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}
	
	public String getResult() {
		return result;
	}
	
	public Object getData() {
		return data;
	}
	
	public String getMessage() {
		return message;
	}
}
