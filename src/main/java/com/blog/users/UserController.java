package com.blog.users;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.UserResponseDto;
import com.blog.posts.Post;

@RestController
@RequestMapping("/api/admin")
public class UserController {

	@Autowired
	UserService ser;
	
	@GetMapping("/users")
	public List<UserResponseDto> getAllUsers(){
		return ser.getUser();
	}
	
	@PutMapping("/users/{id}/role")
	public ResponseEntity<?> update(@PathVariable Long id,@RequestParam Role role){
		User user=ser.updateRole(id, role);
		String newRole = user.getRole().name();
		return ResponseEntity.ok().body(Map.of(
				"message","Role Updated successfully",
				"id",id,
				"newRole",newRole
				));
	}
	
	@GetMapping("/posts/pending")
	public ResponseEntity<List<Post>> getPendingPosts(){
		return ResponseEntity.ok(ser.getPendingPosts());
	}
}
