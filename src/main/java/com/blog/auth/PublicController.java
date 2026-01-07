package com.blog.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.posts.Post;
import com.blog.posts.PostDetailsDTO;
import com.blog.posts.PostRepository;
import com.blog.users.UserRepository;

@RestController
@RequestMapping("/api/public")
@CrossOrigin
public class PublicController {

	@Autowired
	PostRepository repo;

	@Autowired
	UserRepository urepo;
	
	
	@GetMapping("/posts")
	public ResponseEntity<List<Post>> approvedPosts(){
		List<Post> posts = repo.findApprovedPosts();
		return ResponseEntity.ok(posts);
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDetailsDTO> getPostById(@PathVariable Long id) {
	    Post post = repo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
		PostDetailsDTO postDetails = new PostDetailsDTO();
		postDetails.setId(post.getId());
		postDetails.setTitle(post.getTitle());
		postDetails.setContent(post.getContent());
		postDetails.setStatus(post.getStatus());
		postDetails.setCreatedAt(post.getCreatedAt());
		postDetails.setUpdatedAt(post.getUpdatedAt());
		urepo.findById(post.getAuthor()).ifPresent(user->postDetails.setAuthorName(user.getName()));
	    return ResponseEntity.ok(postDetails);
	}

}
