package com.radovan.spring.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.InstanceNotExistException;
import com.radovan.spring.exceptions.SuspendedUserException;
import com.radovan.spring.form.RegistrationForm;
import com.radovan.spring.service.CustomerService;

@Controller
public class MainController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "fragments/login :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String register(ModelMap map) {

		return "fragments/registration :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String storeUser(@ModelAttribute("tempForm") RegistrationForm form) {
		customerService.storeCustomer(form);
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/registerComplete", method = RequestMethod.GET)
	public String registrationCompl() {
		return "fragments/registration_completed :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/registerFail", method = RequestMethod.GET)
	public String registrationFail() {
		return "fragments/registration_failed :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/loggedout", method = RequestMethod.GET)
	public String logout() {
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(null);
		SecurityContextHolder.clearContext();
		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/loginErrorPage", method = RequestMethod.GET)
	public String logError(ModelMap map) {
		map.put("alert", "Invalid username or password!");
		return "fragments/login :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/loginPassConfirm", method = RequestMethod.POST)
	public String confirmLoginPass(Principal principal) {
		Optional<Principal> authPrincipal = Optional.ofNullable(principal);
		if (!authPrincipal.isPresent()) {
			Error error = new Error("Invalid user");
			throw new InstanceNotExistException(error);
		}

		return "fragments/homePage :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/suspensionChecker", method = RequestMethod.POST)
	public String checkForSuspension() {
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (authUser.getEnabled() == (byte) 0) {
			Error error = new Error("Account suspended!");
			throw new SuspendedUserException(error);
		}

		return "fragments/homePage :: ajaxLoadedContent";
	}

}
