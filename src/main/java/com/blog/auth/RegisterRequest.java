package com.blog.auth;

import org.jspecify.annotations.Nullable;

import lombok.Data;

@Data
public class RegisterRequest {

	private String name;
	private String email;
	private String password;
}
