package com.kruthik.exceptions;

public class JobNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JobNotFoundException(String msg) {
		super(msg);
	}

}
