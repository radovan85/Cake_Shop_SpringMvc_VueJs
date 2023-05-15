package com.radovan.spring.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.UserService;

@RestController
@RequestMapping(value = "/api/customers")
public class CustomerRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@Secured(value = "ROLE_USER")
	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
	public ResponseEntity<UserDto> getCurrentUser() {
		UserDto currentUser = userService.getCurrentUser();
		return ResponseEntity.ok(currentUser);
	}

	@Secured(value = "ROLE_USER")
	@RequestMapping(value = "/getCurentCustomer", method = RequestMethod.GET)
	public ResponseEntity<CustomerDto> getCurrentCustomer() {
		UserDto currentUser = userService.getCurrentUser();
		CustomerDto currentCustomer = customerService.getCustomerByUserId(currentUser.getId());
		return ResponseEntity.ok().body(currentCustomer);
	}

	@PreAuthorize(value="hasAuthority('ADMIN')")
	@RequestMapping(value = "/getUserData/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<UserDto> getUserData(@PathVariable("customerId") Integer customerId) {
		CustomerDto customer = customerService.getCustomer(customerId);
		UserDto user = userService.getUserById(customer.getUserId());
		return ResponseEntity.ok().body(user);
	}

}
