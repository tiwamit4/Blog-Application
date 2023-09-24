package com.amitcode.blog.services;

import java.util.List;

import com.amitcode.blog.payloads.PostDto;
import com.amitcode.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all posts
	//List <PostDto> getAllPost();
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy);

	
	//get Single post
	PostDto getPostById(Integer postId);
	
	//get all post by category
	List <PostDto> getPostsByCategory(Integer categoryId);
	
	//get all posts by user
	List <PostDto> getPostByUser(Integer userId);
	
	//search posts
	List <PostDto> searchPosts(String KeyWord);
	
}
