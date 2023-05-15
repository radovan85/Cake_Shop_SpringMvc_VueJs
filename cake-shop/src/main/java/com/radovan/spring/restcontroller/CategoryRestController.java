package com.radovan.spring.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CategoryDto;
import com.radovan.spring.service.CategoryService;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryRestController {
	
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/allCategories",method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> allCategories = categoryService.listAll();
		return ResponseEntity.ok().body(allCategories);
	}
	
	@RequestMapping(value = "/categoryDetails/{categoryId}",method = RequestMethod.GET)
	public ResponseEntity<CategoryDto> getCategoryDetails(@PathVariable ("categoryId") Integer categoryId){
		CategoryDto category = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok().body(category);
	}
}
