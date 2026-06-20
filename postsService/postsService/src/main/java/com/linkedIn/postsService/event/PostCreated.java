package com.linkedIn.postsService.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreated {
    private Long postOwnerId;
    private Long postId;
    private Long userId;
    private String content;
}
