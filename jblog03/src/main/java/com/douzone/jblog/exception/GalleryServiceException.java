package com.douzone.jblog.exception;

public class GalleryServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GalleryServiceException() {
		super("GalleryServiceException Occurs");
	}

	public GalleryServiceException(String message) {
		super(message);
	}

}