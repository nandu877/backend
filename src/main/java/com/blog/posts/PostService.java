package com.blog.posts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.users.UserRepository;

@Service
public class PostService {
	@Autowired
	PostRepository repo;
	
	@Autowired
	UserRepository urepo;
	
	public List<Post> getPosts(){
		return repo.findAll();
	}
	
	public Post getPost(Long id) {
		Post post = repo.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
		return post;
	}
	
	public List<Post> getPostsByUserId(Long userId){
		List<Post> posts = repo.findByAuthor(userId);
		return posts;
	}
	
	public Post createPost(PostRequest req) {
		Post post = new Post();
		post.setAuthor(req.getUserId());
		post.setTitle(req.getTitle());
		post.setContent(req.getContent());
		post.setCreatedAt(LocalDateTime.now());
		post.setUpdatedAt(LocalDateTime.now());
		return repo.save(post);
	}
	
	public Post updatePost(PostRequest postReq,Long id,Long userId) {
		Post post = repo.getById(id);
		if(post.getAuthor()==(userId)) {
			
			post.setTitle(postReq.getTitle());
			post.setContent(postReq.getContent());
			post.setUpdatedAt(LocalDateTime.now());
			post.setStatus(PostStatus.PENDING);
			return repo.save(post);
		}
		else {
			throw new RuntimeException("User doesn't belogs to this post");
		}
	}
	
	public Post updateStatus(Long id,PostStatus status) {
		Post post = repo.findById(id).orElseThrow(()->new RuntimeException("Post not found"));
		post.setStatus(status);
		return repo.save(post);
	}
	
	public boolean delete(Long id) {
		
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		}
		else {
			throw new RuntimeException("Post Not Exists");
		}
	}
	
	
	
}
