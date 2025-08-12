package com.kruthik.exceptions;

public class ResumeRequiredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResumeRequiredException(String msg) {
		super(msg);
	}

}
