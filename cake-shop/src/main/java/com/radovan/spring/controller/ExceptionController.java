package com.radovan.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.radovan.spring.exceptions.DataNotValidatedException;
import com.radovan.spring.exceptions.ExistingCategoryException;
import com.radovan.spring.exceptions.ExistingEmailException;
import com.radovan.spring.exceptions.InstanceNotExistException;
import com.radovan.spring.exceptions.InvalidCartException;
import com.radovan.spring.exceptions.SuspendedUserException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(InstanceNotExistException.class)
	public ResponseEntity<String> handleInstanceNotExistException(InstanceNotExistException exc) {
		return ResponseEntity.internalServerError().body("Instance not found!!!");
	}

	@ExceptionHandler(DataNotValidatedException.class)
	public ResponseEntity<String> handleDataNotValidatedException(DataNotValidatedException exc) {
		return ResponseEntity.internalServerError().body("Data not validated!!!");
	}

	@ExceptionHandler(ExistingCategoryException.class)
	public ResponseEntity<String> handleExistingCategoryException(ExistingCategoryException exc) {
		return ResponseEntity.internalServerError().body("Category exists already!!!");
	}

	@ExceptionHandler(ExistingEmailException.class)
	public ResponseEntity<String> handleExistingEmailException(ExistingEmailException exc) {
		return ResponseEntity.internalServerError().body("Email exists already!!!");
	}

	@ExceptionHandler(InvalidCartException.class)
	public ResponseEntity<String> handleInvalidCartException(InvalidCartException exc) {
		return ResponseEntity.internalServerError().body("Invalid Cart!!!");
	}

	@ExceptionHandler(SuspendedUserException.class)
	public ResponseEntity<String> handleSuspendedUserException(SuspendedUserException exc) {
		return ResponseEntity.internalServerError().body("Account suspended!!!");
	}

}
