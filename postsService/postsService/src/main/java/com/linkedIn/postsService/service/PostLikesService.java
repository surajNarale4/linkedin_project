package com.linkedIn.postsService.service;


import com.linkedIn.postsService.entity.Post;
import com.linkedIn.postsService.exception.ResourceNotFoundException;
import com.linkedIn.postsService.repository.PostLikesRepository;
import com.linkedIn.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikesService {
    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    public void likePost(Long postId) throws BadRequestException {
        Long userId=1L;
        log.info("user with Id:{} liking the post with id:{}",userId,postId);
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id "+postId));
        boolean hasAlreadyliked = postLikesRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyliked) throw new BadRequestException("cannot like the post again");
    }
}
