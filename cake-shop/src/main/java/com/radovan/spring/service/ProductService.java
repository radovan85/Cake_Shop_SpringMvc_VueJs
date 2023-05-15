package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.ProductDto;

public interface ProductService {

	ProductDto addProduct(ProductDto product);
	
	ProductDto updateProduct(Integer productId,ProductDto product);
	
	ProductDto getProductById(Integer productId);
	
	void deleteProduct(Integer productId);
	
	List<ProductDto> listAll();
	
	List<ProductDto> listAllByCategoryId(Integer categoryId);
	
}
