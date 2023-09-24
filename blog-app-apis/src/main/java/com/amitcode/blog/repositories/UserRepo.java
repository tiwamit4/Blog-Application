package com.amitcode.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amitcode.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>
{
	
}
