package com.afronet.springwebclient.controller;

import com.afronet.springwebclient.entity.Post;
import com.afronet.springwebclient.service.PostServiceClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostServiceClient postServiceClient;


    public PostController(PostServiceClient postServiceClient) {
        this.postServiceClient = postServiceClient;
    }

    @GetMapping("/{id}")
    public Mono<Post> getPostById(@PathVariable int id){

        return postServiceClient.getPostById(id);
    }
    @GetMapping("/{id}/{comment}")
    public Mono<Post> getPostCommentById(@PathVariable int id, @PathVariable String comment){

        return postServiceClient.getPostById(id);
    }

    @GetMapping
    public Flux<Post> getAllPosts(){
        return postServiceClient.getAllPosts();
    }

    @PostMapping
    public Mono<Post> addPost(@RequestBody Post post){
        return postServiceClient.createPost(post);
    }

    @PutMapping("/{id}")
    public Mono<Post> updatePost(@PathVariable int id, @RequestBody Post post){
        return postServiceClient.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public Mono<Post> removePost(@PathVariable int id){
        return postServiceClient.deletePost(id);
    }

}
