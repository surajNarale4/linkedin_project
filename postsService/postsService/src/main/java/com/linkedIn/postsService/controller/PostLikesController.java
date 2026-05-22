package com.linkedIn.postsService.controller;


import com.linkedIn.postsService.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikesService postLikesService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId){
        postLikesService.likePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId){
        postLikesService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

}
