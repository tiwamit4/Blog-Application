package com.amitcode.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amitcode.blog.config.AppConstants;
import com.amitcode.blog.payloads.ApiResponse;
import com.amitcode.blog.payloads.PostDto;
import com.amitcode.blog.payloads.PostResponse;
import com.amitcode.blog.services.FileService;
import com.amitcode.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	//create
	@RequestMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable Integer userId,@PathVariable Integer categoryId){
		
		PostDto createPost= this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	//get By user
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List <PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>> (posts,HttpStatus.OK);
		
	}
	
	
	//get By Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List <PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> posts = this.postService.getPostByUser(categoryId);
		return new ResponseEntity<List<PostDto>> (posts,HttpStatus.OK);
		
	}
	
	
	//get all posts
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue =AppConstants.PAGE_NUMBER, required=false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORY_BY,required = false)String sortBy)
	{
		PostResponse postResponse =this.postService.getAllPost(pageNumber,pageSize,sortBy);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);	
		
	}
	
	
	
	//get post detail by id
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		
		PostDto postDto=postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
		
	}
	
	//delete a post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post is sucessfully deleted  ",true);
		
	}
	
	//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);	
		
	}
	
	//search
	@GetMapping("/posts/search/{keyWord}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keyWord") String keyWord)
	{
		List<PostDto> result=this.postService.searchPosts(keyWord);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
		
	}
	
	//post image upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto>  uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException
	{
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		
	}	
	
	//method to serve file
	
	@GetMapping(value="post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
		
	}
	
	
}




















