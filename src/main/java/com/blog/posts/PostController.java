package com.blog.posts;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.users.User;
import com.blog.users.UserRepository;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

	@Autowired
	PostService ser;

	@Autowired
	UserRepository urepo;
	
	
	@GetMapping("/my-posts")
	public ResponseEntity<List<PostDetailsDTO>> getPosts(){
		List<Post> list = ser.getPosts();
		List<PostDetailsDTO> postDetails = list.stream().map(post ->{
			PostDetailsDTO dto = new PostDetailsDTO();
			dto.setId(post.getId());
			dto.setTitle(post.getTitle());
			dto.setContent(post.getContent());
			dto.setStatus(post.getStatus());
			dto.setCreatedAt(post.getCreatedAt());
			dto.setUpdatedAt(post.getUpdatedAt());

			urepo.findById(post.getAuthor()).ifPresent(user->dto.setAuthorName(user.getName()));
			return dto;
		}).toList();
		return ResponseEntity.ok(postDetails);
	}
	
	@GetMapping("/my-post/{id}")
	
	
	public ResponseEntity<PostDetailsDTO> getPost(@PathVariable Long id){
		Post post = ser.getPost(id);
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
	
	@GetMapping("/my-posts/{author}")
	public ResponseEntity<List<Post>> getUserIdPosts(@PathVariable Long author){
		List<Post> list = ser.getPostsByUserId(author);
		return ResponseEntity.ok(list);
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<?> addPost(@RequestBody PostRequest req){
		Post post = ser.createPost(req);
		PostResponse response = new PostResponse(post.getId(),post.getTitle(),post.getStatus());
		return ResponseEntity.ok().body(Map.of("message","Post created and pending approval","post",response));
	}
	
	@PutMapping("/update/{id}/{userId}")
	public ResponseEntity<?> updatePost(@PathVariable Long id,@PathVariable Long userId,@RequestBody PostRequest postReq){
		return ResponseEntity.ok(ser.updatePost(postReq, id, userId));
	}

	@PutMapping("/update-status/{id}/status")
	public ResponseEntity<?> updateStatus(@PathVariable Long id,@RequestParam PostStatus status){
		Post post=ser.updateStatus(id, status);
		return ResponseEntity.ok().body(Map.of(
				"message","Status Updated successfully",
				"id",id,
				"newRole",status
				));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id){
		boolean deleted=ser.delete(id);
		if(deleted) 
			return ResponseEntity.ok().body(Map.of("message","Post deleted successfully","postId",id));
		
		return ResponseEntity.ok().body(Map.of("message","Post not deleted successfully","postId",id));
	}
}
