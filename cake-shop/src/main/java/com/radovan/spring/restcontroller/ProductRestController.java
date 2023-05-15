package com.radovan.spring.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.service.ProductService;

@RestController
@RequestMapping(value = "/api/products")
public class ProductRestController {

	@Autowired
	private ProductService productService;
	
	
	
	@RequestMapping(value = "/allProducts",method = RequestMethod.GET)
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		List<ProductDto> allProducts = productService.listAll();
		return ResponseEntity.ok().body(allProducts);
	} 
	
	@RequestMapping(value = "/allProducts/{categoryId}",method = RequestMethod.GET)
	public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable ("categoryId") Integer categoryId){
		List<ProductDto> allProducts = productService.listAllByCategoryId(categoryId);
		return ResponseEntity.ok().body(allProducts);
	} 
	
	@RequestMapping(value = "/productDetails/{productId}",method = RequestMethod.GET)
	public ResponseEntity<ProductDto> getProductDetails(@PathVariable ("productId") Integer productId){
		ProductDto product = productService.getProductById(productId);
		return ResponseEntity.ok().body(product);
	}
	
	
	
	
}
