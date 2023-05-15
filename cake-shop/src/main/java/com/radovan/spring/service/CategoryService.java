package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto category);
	
	List<CategoryDto> listAll();
	
	CategoryDto updateCategory(Integer categoryId, CategoryDto category);
	
	void deleteCategory(Integer categoryId);
	
	CategoryDto getCategoryById(Integer categoryId);
	
}
