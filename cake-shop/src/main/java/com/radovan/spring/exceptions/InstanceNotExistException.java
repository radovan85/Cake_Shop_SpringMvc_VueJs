package com.radovan.spring.exceptions;

import javax.management.RuntimeErrorException;

public class InstanceNotExistException extends RuntimeErrorException{

	public InstanceNotExistException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

}
