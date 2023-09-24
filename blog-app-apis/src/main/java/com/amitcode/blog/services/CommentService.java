package com.amitcode.blog.services;

import com.amitcode.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);
}
