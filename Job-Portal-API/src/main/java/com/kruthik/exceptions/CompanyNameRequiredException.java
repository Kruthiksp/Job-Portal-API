package com.kruthik.exceptions;

public class CompanyNameRequiredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CompanyNameRequiredException(String msg) {
		super(msg);
	}
}
