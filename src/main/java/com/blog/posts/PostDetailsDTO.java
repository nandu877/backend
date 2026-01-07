package com.blog.posts;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
public class PostDetailsDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
