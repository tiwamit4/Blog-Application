package com.amitcode.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	
	String resourceName;
	String fieldName;
	long fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(resourceName+" not found with "+fieldName+" : "+fieldValue);
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
}
