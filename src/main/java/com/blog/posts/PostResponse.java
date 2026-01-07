package com.blog.posts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponse {

	private Long id;
	private String title;
	private PostStatus status;
}
