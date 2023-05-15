package com.radovan.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/mvc/admin")
public class AdminController {

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String adminPanel() {
		return "fragments/admin :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/allCategories",method = RequestMethod.GET)
	public String getAllCategories() {
		return "fragments/categoryList :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/addCategory",method=RequestMethod.GET)
	public String renderCategoryForm() {
		return "fragments/categoryForm :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/updateCategory/{categoryId}",method = RequestMethod.GET)
	public String renderUpdateCategoryForm(@PathVariable("categoryId") Integer categoryId) {
		return "fragments/categoryUpdateForm :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/addProduct",method = RequestMethod.GET)
	public String renderProductForm() {
		return "fragments/productForm :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/updateProduct/{productId}",method = RequestMethod.GET)
	public String renderProductUpdateForm(@PathVariable ("productId") Integer productId,ModelMap map) {
		map.put("productId", productId);
		return "fragments/productUpdateForm :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/allCustomers",method = RequestMethod.GET)
	public String getAllCustomers() {
		return "fragments/customerList :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/allOrders",method = RequestMethod.GET)
	public String getAllOrders() {
		return "fragments/orderList :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/orderDetails/{orderId}",method = RequestMethod.GET)
	public String getOrderDetails(@PathVariable ("orderId") Integer orderId,ModelMap map) {
		map.put("orderId", orderId);
		return "fragments/orderDetails :: ajaxLoadedContent";
	}
}
