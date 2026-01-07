package com.blog.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.JwtUtils;
import com.blog.dto.LoginResponse;
import com.blog.dto.RegisterResponse;
import com.blog.dto.UserResponseDto;
import com.blog.users.User;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	JwtUtils util;
	
	@Autowired
	AuthService ser;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> createUser(@RequestBody RegisterRequest req) {
		User user = ser.addUser(req);
		UserResponseDto userDto = new UserResponseDto(user.getId(),
				user.getName(),
				user.getEmail(),
				user.getRole().name());
		RegisterResponse response = new RegisterResponse("User registered successfully",userDto);
		return ResponseEntity.ok(response);
		
	} 
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest LoginReq){
		User user = ser.login(LoginReq);
		String token = util.generateToken(user.getEmail());
		UserResponseDto userResponse = new UserResponseDto(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getRole().name());
		LoginResponse response = new LoginResponse("Login Suucessfully",token,userResponse);
		
		return ResponseEntity.ok(response);
	}

}
