package com.amitcode.blog.services;

import java.util.List;

import com.amitcode.blog.payloads.UserDto;


public interface UserService 
{
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer userId);
	
	UserDto getUserById(Integer UserId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
