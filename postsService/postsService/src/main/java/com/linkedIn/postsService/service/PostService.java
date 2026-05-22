package com.linkedIn.postsService.service;

import com.linkedIn.postsService.dto.PostCreateRequestDTO;
import com.linkedIn.postsService.dto.PostDTO;
import com.linkedIn.postsService.entity.Post;
import com.linkedIn.postsService.exception.ResourceNotFoundException;
import com.linkedIn.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDTO createPost(PostCreateRequestDTO postCreateDTO, long userId) {
        log.info("creating post of user id {}:",userId);
        Post post = modelMapper.map(postCreateDTO,Post.class);
        post.setUserId(userId);
        post=postRepository.save(post);
        return modelMapper.map(post,PostDTO.class);
    }

    public PostDTO getPostById(Long postId) {
        log.info("getting post by id {}:",postId);
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("no resource found for "+postId));
        return modelMapper.map(post,PostDTO.class);
    }


    public List<PostDTO> getAllPostOfUser(long userId){
        log.info("getting all post of user id {}:",userId);
        List<Post> allPosts= postRepository.findByUserId(userId);
        return allPosts
                .stream()
                .map(post -> modelMapper.map(post,PostDTO.class))
                .collect(Collectors.toList());
    }

}
