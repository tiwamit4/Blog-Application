package com.amitcode.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.amitcode.blog.entities.Category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amitcode.blog.exceptions.ResourceNotFoundException;
import com.amitcode.blog.payloads.CategoryDto;
import com.amitcode.blog.repositories.CategoryRepo;
import com.amitcode.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat =this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addedCat, CategoryDto.class);
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat =this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId)); 
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescriptionString(categoryDto.getCategoryDescription());
		Category UpdatedCat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(UpdatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category cat=this.categoryRepo.findById(categoryId).
				orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {

		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {

		List <Category> categories = this.categoryRepo.findAll();
		List <CategoryDto> catDtos = categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return catDtos;
	}

}
