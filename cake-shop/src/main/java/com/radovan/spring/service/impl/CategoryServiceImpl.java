package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.CategoryDto;
import com.radovan.spring.entity.CategoryEntity;
import com.radovan.spring.exceptions.ExistingCategoryException;
import com.radovan.spring.exceptions.InstanceNotExistException;
import com.radovan.spring.repository.CategoryRepository;
import com.radovan.spring.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public CategoryDto addCategory(CategoryDto category) {
		// TODO Auto-generated method stub
		Optional<CategoryEntity> categoryOpt = Optional.ofNullable(categoryRepository.findByName(category.getName()));
		if (categoryOpt.isPresent()) {
			Error error = new Error("Existing category");
			throw new ExistingCategoryException(error);
		}
		CategoryEntity categoryEntity = tempConverter.categoryDtoToEntity(category);
		CategoryEntity storedCategory = categoryRepository.save(categoryEntity);
		CategoryDto returnValue = tempConverter.categoryEntityToDto(storedCategory);
		return returnValue;
	}

	@Override
	public List<CategoryDto> listAll() {
		// TODO Auto-generated method stub
		List<CategoryDto> returnValue = new ArrayList<CategoryDto>();
		Optional<List<CategoryEntity>> allCategoriesOpt = Optional.ofNullable(categoryRepository.findAll());
		if (!allCategoriesOpt.isEmpty()) {
			allCategoriesOpt.get().forEach((category) -> {
				CategoryDto categoryDto = tempConverter.categoryEntityToDto(category);
				returnValue.add(categoryDto);
			});
		}
		return returnValue;
	}

	@Override
	public CategoryDto updateCategory(Integer categoryId, CategoryDto category) {
		// TODO Auto-generated method stub
		Optional<CategoryEntity> categoryOpt = Optional.ofNullable(categoryRepository.findByName(category.getName()));
		if (categoryOpt.isPresent()) {
			Error error = new Error("Existing category");
			throw new ExistingCategoryException(error);
		}
		CategoryDto returnValue = null;
		categoryOpt = categoryRepository.findById(categoryId);
		if (categoryOpt.isPresent()) {
			category.setCategoryId(categoryId);
			CategoryEntity categoryEntity = tempConverter.categoryDtoToEntity(category);
			CategoryEntity updatedCategory = categoryRepository.saveAndFlush(categoryEntity);
			returnValue = tempConverter.categoryEntityToDto(updatedCategory);
		} else {
			Error error = new Error("Instance not found");
			throw new InstanceNotExistException(error);
		}
		return returnValue;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryId);
		if (categoryOpt.isPresent()) {
			categoryRepository.deleteById(categoryId);
			categoryRepository.flush();
		} else {
			Error error = new Error("Instance not found");
			throw new InstanceNotExistException(error);
		}
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		CategoryDto returnValue = null;
		Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryId);
		if (categoryOpt.isPresent()) {
			returnValue = tempConverter.categoryEntityToDto(categoryOpt.get());
		} else {
			Error error = new Error("Category Not Found");
			throw new InstanceNotExistException(error);
		}
		return returnValue;
	}

}
