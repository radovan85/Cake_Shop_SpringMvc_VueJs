package com.radovan.spring.exceptions;

import javax.management.RuntimeErrorException;

public class ExistingCategoryException extends RuntimeErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingCategoryException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
