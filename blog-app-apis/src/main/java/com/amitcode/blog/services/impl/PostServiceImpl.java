package com.amitcode.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amitcode.blog.entities.Category;
import com.amitcode.blog.entities.Post;
import com.amitcode.blog.entities.User;
import com.amitcode.blog.exceptions.ResourceNotFoundException;
import com.amitcode.blog.payloads.PostDto;
import com.amitcode.blog.payloads.PostResponse;
import com.amitcode.blog.repositories.CategoryRepo;
import com.amitcode.blog.repositories.PostRepo;
import com.amitcode.blog.repositories.UserRepo;
import com.amitcode.blog.services.PostService;


@Service

public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "User id",userId ));
		
		Category category=this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId));
		
		Post post= this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=this.postRepo.save(post);
		
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
			
		Post post=this.postRepo.findById(postId).
				orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		post.setTitle(postDto.getTitle()); 
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost   =this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post=this.postRepo.findById(postId).
				orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy) {

		//int pageSize=3;
		//int pageNumber=1;
		
		Pageable p=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy)/*.descending()*/);
		
		//List <Post> allposts=this.postRepo.findAll();
		
		Page<Post> pagePost = this.postRepo.findAll(p); 
		List <Post> allposts=pagePost.getContent();
		
		List <PostDto> postDtos= allposts.stream().map((post)->this.modelMapper.
				map(post, PostDto.class)).collect(Collectors.toList());
				
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());


		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {

		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		List <Post> posts=this.postRepo.findByCategory(cat);
		
		List <PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		List <Post> posts=this.postRepo.findByUser(user);
		
		List <PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String KeyWord) {

		List <Post> posts = this.postRepo.findByTitleContaining(KeyWord);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

		
	
}
