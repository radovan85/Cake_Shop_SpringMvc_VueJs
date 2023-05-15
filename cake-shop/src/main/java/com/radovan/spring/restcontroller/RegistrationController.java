package com.radovan.spring.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.exceptions.DataNotValidatedException;
import com.radovan.spring.form.RegistrationForm;
import com.radovan.spring.service.CustomerService;

@RestController
@RequestMapping(value = "/api/registration")
public class RegistrationController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/storeUser", method = RequestMethod.POST)
	public ResponseEntity<String> registerUser(@Validated @RequestBody RegistrationForm form, Errors errors) {

		if (errors.hasErrors()) {
			Error error = new Error("Not validated data!");
			throw new DataNotValidatedException(error);
		}

		CustomerDto storedCustomer = customerService.storeCustomer(form);
		return ResponseEntity.ok().body("Customer stored with id " + storedCustomer.getCustomerId());

	}
}
