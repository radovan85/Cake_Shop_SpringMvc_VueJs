package com.radovan.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/mvc/products")
public class ProductController {

	@RequestMapping(value = "/allProducts",method = RequestMethod.GET)
	public String getAllProducts() {
		return "fragments/productList :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/shop",method = RequestMethod.GET)
	public String shopContent() {
		return "fragments/shop :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/productDetails/{productId}",method=RequestMethod.GET)
	public String getProductDetails(@PathVariable ("productId") Integer productId,ModelMap map) {
		map.put("productId", productId);
		return "fragments/productDetails :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/shop/{categoryId}",method = RequestMethod.GET)
	public String shopContentByCategory(@PathVariable ("categoryId") Integer categoryId,ModelMap map) {
		map.put("categoryId", categoryId);
		return "fragments/shop_category :: ajaxLoadedContent";
	}
	
	
}
