package com.linkedIn.postsService.controller;


import com.linkedIn.postsService.auth.AuthContextHolder;
import com.linkedIn.postsService.dto.PostCreateRequestDTO;
import com.linkedIn.postsService.dto.PostDTO;
import com.linkedIn.postsService.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("core")
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDTO postCreateDTO){
        PostDTO postDTO=postService.createPost(postCreateDTO,1L);
        return new ResponseEntity<>(postDTO, HttpStatusCode.valueOf(201));
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {

        log.info("current userid is{}",AuthContextHolder.getCurrentUserId());
        PostDTO postDTO=postService.getPostById(postId);
        return new ResponseEntity<>(postDTO,HttpStatusCode.valueOf(200));
    }

    @GetMapping("users/{userId}/allPosts")
    public ResponseEntity<List<PostDTO>> getAllPostsOfUser(@PathVariable Long userId){
        List<PostDTO> allPosts=postService.getAllPostOfUser(userId);
        return new ResponseEntity<>(allPosts,HttpStatusCode.valueOf(200));
    }



}
