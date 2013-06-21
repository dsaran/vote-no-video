package com.github.dsaran.video.exception;

public class VideoRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public VideoRuntimeException() {
		super();
	}

	public VideoRuntimeException(Throwable t) {
		super(t);
	}
	
}
