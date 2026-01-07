package com.blog.posts;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Column(columnDefinition = "LONGTEXT")
	private String content;
	private Long author;
	@Enumerated(EnumType.STRING)
	private PostStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist
	void onCreate() {
		createdAt=LocalDateTime.now();
		status = PostStatus.PENDING;
	}
	
	@PreUpdate
	void onUpdate() {
		updatedAt=LocalDateTime.now();
	}
	
}
