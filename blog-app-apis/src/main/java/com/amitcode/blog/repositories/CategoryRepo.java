package com.amitcode.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amitcode.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>
{

	
	
}
