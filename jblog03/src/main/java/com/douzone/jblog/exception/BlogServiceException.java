package com.douzone.jblog.exception;

public class BlogServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BlogServiceException() {
		super("BlogServiceException Occurs");
	}

	public BlogServiceException(String message) {
		super(message);
	}

}