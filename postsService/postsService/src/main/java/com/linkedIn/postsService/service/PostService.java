package com.linkedIn.postsService.service;

import com.linkedIn.postsService.auth.AuthContextHolder;
import com.linkedIn.postsService.client.ConnectionServiceClient;
import com.linkedIn.postsService.dto.PersonDTO;
import com.linkedIn.postsService.dto.PostCreateRequestDTO;
import com.linkedIn.postsService.dto.PostDTO;
import com.linkedIn.postsService.entity.Post;
import com.linkedIn.postsService.event.PostCreated;
import com.linkedIn.postsService.exception.ResourceNotFoundException;
import com.linkedIn.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionServiceClient connectionServiceClient;
    private final KafkaTemplate<Long,PostCreated> postCreatedKafkaTemplate;

    public PostDTO createPost(PostCreateRequestDTO postCreateDTO, long userId) {
        log.info("creating post of user id {}:",userId);
        Post post = modelMapper.map(postCreateDTO,Post.class);
        post.setUserId(userId);
        post=postRepository.save(post);
        Set<Long> userIds=connectionServiceClient.getFirstDegreeConnections(userId).stream().map(p->p.getUserId()).collect(Collectors.toSet());
       //sending notitication to consumer users
        for(Long id :userIds){
            PostCreated postCreated= PostCreated.builder()
                    .postId(post.getId())
                    .content(post.getContent())
                    .userId(id)
                    .postOwnerId(post.getUserId())
                    .build();
            postCreatedKafkaTemplate.send("post_created_topic",postCreated);
        }
        return modelMapper.map(post,PostDTO.class);
    }


    public PostDTO getPostById(Long postId) {
        log.info("getting post by id {}:",postId);
        Long correntUser =AuthContextHolder.getCurrentUserId();
        //TODO Will Remove in future
        //Call the connection service from post service
        // and pass the user-id in the header
        List<PersonDTO> personDTOList =connectionServiceClient.getFirstDegreeConnections(correntUser);
        log.info("we got all first degree connection's {}",personDTOList);

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
