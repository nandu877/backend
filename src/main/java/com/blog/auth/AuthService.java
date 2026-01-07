package com.blog.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.JwtUtils;
import com.blog.users.User;
import com.blog.users.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils util;
	
	public User addUser(RegisterRequest req) {
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPassword(encoder.encode(req.getPassword()));
		return repo.save(user);
	}
	
	public User login(LoginRequest loginReq) {
		User user = repo.findByEmail(loginReq.getEmail()).orElseThrow(()-> new RuntimeException("Invalid crendentails"));
		if(!encoder.matches(loginReq.getPassword(),user.getPassword())) {
			throw new RuntimeException("Invalid Credentials");
		}
		return user;
	}

}
