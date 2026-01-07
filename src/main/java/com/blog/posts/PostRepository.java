package com.blog.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
	@Query("SELECT p FROM Post p WHERE p.status = 'APPROVED'")
	List<Post> findApprovedPosts();
	@Query("SELECT p FROM Post p WHERE p.status = 'PENDING'")
	List<Post> findPendingPosts();
	@Query("SELECT p FROM Post p WHERE p.status = 'REJECTED'")
	List<Post> findRejectedPosts();
	List<Post> findByAuthor(Long author);
}
