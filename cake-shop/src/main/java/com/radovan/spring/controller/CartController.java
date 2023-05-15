package com.radovan.spring.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.radovan.spring.entity.UserEntity;

@Controller
@RequestMapping(value = "/mvc/cart")
public class CartController {

	@RequestMapping(value = "/cart",method = RequestMethod.GET)
	public String goToCart() {
		return "fragments/cart :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/checkout",method = RequestMethod.GET)
	public String checkoutPage(ModelMap map) {
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		map.put("userId", authUser.getId());
		return "fragments/checkout :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/orderCompleted",method = RequestMethod.GET)
	public String orderCompleted() {
		return "fragments/orderPlaced :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/cartError",method = RequestMethod.GET)
	public String cartError() {
		return "fragments/invalidCart :: ajaxLoadedContent";
	}
}
