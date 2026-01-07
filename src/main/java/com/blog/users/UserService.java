package com.blog.users;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dto.UserResponseDto;
import com.blog.posts.Post;
import com.blog.posts.PostRepository;
import com.blog.posts.PostStatus;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	PostRepository prepo;
	
	public List<UserResponseDto> getUser() {
		
		List<User> users= repo.findAll();
		return users.stream().map(user->new UserResponseDto(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()))
				.toList();
	}
	
	public User updateRole(Long id, Role role) {

	    User user = repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    user.setRole(role);
	    return repo.save(user);
	}
	
	public List<Post> getPendingPosts() {
	    return prepo.findPendingPosts();
	}

}
