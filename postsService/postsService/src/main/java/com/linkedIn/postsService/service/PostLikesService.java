package com.linkedIn.postsService.service;


import com.linkedIn.postsService.auth.AuthContextHolder;
import com.linkedIn.postsService.entity.Post;
import com.linkedIn.postsService.entity.PostLikes;
import com.linkedIn.postsService.event.PostLiked;
import com.linkedIn.postsService.exception.BadRequestException;
import com.linkedIn.postsService.exception.ResourceNotFoundException;
import com.linkedIn.postsService.repository.PostLikesRepository;
import com.linkedIn.postsService.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.security.auth.AuthenticationContext;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikesService {
    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<Long,PostLiked> postLikesKafkaTemplate;

    @Transactional
    public void likePost(Long postId) {
        Long userId=1L;
        log.info("user with Id:{} liking the post with id:{}",userId,postId);
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id "+postId));
        boolean hasAlreadyliked = postLikesRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyliked) throw new BadRequestException("cannot like the post again");
        PostLikes postLike = new PostLikes();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikesRepository.save(postLike);
        //Send the notification to Ownner as someone is liked your post
        Long likedByUser=AuthContextHolder.getCurrentUserId();
        PostLiked postLiked=PostLiked.builder()
                .postId(postId)
                .likedByUserId(likedByUser)
                .postOwnerId(post.getUserId())
                .build();
        postLikesKafkaTemplate.send("post_liked_topic",postLiked);

    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId=1L;
        log.info("user with Id:{} diss-liking the post with id:{}",userId,postId);
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id "+postId));
        boolean hasAlreadyliked = postLikesRepository.existsByUserIdAndPostId(userId,postId);
        if(!hasAlreadyliked) throw new BadRequestException("you cannot unlike the post that you not liked");
        postLikesRepository.deleteByUserIdAndPostId(userId,postId);


    }
}
