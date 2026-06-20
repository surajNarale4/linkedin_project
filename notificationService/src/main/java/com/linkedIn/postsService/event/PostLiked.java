package com.linkedIn.postsService.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLiked {
    private Long postId;
    private Long postOwnerId;
    private Long likedByUserId;
}
