package com.blog.dto;

import com.blog.users.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

		private Long id;
	    private String name;
	    private String email;
	    private String role;
}
