package com.douzone.jblog.dto;

public class JsonRrsult {
	private String result;/*"success"or "fail"*/
	private Object date;/*if success,set*/
	private String message;
	
	// 기본 생성자를 프라이빗으로 만들어서 참조를 막는다
	private JsonRrsult() {}
	
	private JsonRrsult(Object date) {
		result = "success";
		this.date = date;
		message = null;
	}
	

	private JsonRrsult(String message) {
		result = "fail";
		date = null;
		this.message = message;
	}
	
	public static JsonRrsult success(Object date) {
		return new JsonRrsult(date);
	}
	public static JsonRrsult fail(String message) {
		return new JsonRrsult(message);
	}
	
	public String getResult() {
		return result;
	}
	
	public Object getDate() {
		return date;
	}
	
	public String getMessage() {
		return message;
	}
}
