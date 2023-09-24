package com.amitcode.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4,message = "Username must be min of 4 character")
	private String name;
	
	@Email(message = "Invalid Email")
	private String email;
	
	@NotEmpty 
	@Size(min = 3,max = 10,message = "PassWord Must be Minimum of 3 char And Maximum of 10 char")
	//@Pattern(regexp = "")
	String password;
	
	@NotEmpty
	@Size(min = 4 ,max = 10 ,message = "Password be min of 4 char and max of 10;")
	private String about;
}
