package com.afronet.springwebclient.service;

import com.afronet.springwebclient.entity.Post;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostServiceClient {

    private final WebClient webClient;

    public PostServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // create new post
    public Mono<Post> createPost(Post post){
    return webClient
        .post()
        .bodyValue(post)
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            clientResponse -> Mono.error(new RuntimeException("Client Error")))
        .onStatus(
            HttpStatusCode::is5xxServerError,
            clientResponse -> Mono.error(new RuntimeException("Server Error")))
        .bodyToMono(Post.class)
        .onErrorResume(WebClientRequestException.class, e -> Mono.error(new RuntimeException("Error: " + e.getMessage())));
    }

    // get Post by id
    public Mono<Post> getPostById(int id){
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new RuntimeException("Not Found")))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(Post.class)
                .onErrorResume(WebClientRequestException.class, e -> Mono.error(new RuntimeException("Error: " + e.getMessage())));
    }

    //get all posts
    public Flux<Post> getAllPosts(){
        return webClient.get()
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new RuntimeException("Client Error")))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Server Error")))
                .bodyToFlux(Post.class)
                .onErrorResume(WebClientRequestException.class, e -> Mono.error(new RuntimeException("Error: " + e.getMessage())));
    }

    // update post
    public Mono<Post> updatePost(int id, Post post){
        return webClient.put()
                .uri("/{id}", id)
                .bodyValue(post)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new RuntimeException("Not Found")))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(Post.class)
                .onErrorResume(WebClientRequestException.class, e -> Mono.error(new RuntimeException("Error: " + e.getMessage())));
    }


    // delete a post
    public Mono<Post> deletePost(int id){
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new RuntimeException("Not Found")))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new RuntimeException("Server Error")))
                .bodyToMono(Post.class)
                .onErrorResume(WebClientRequestException.class, e -> Mono.error(new RuntimeException("Error: " + e.getMessage())));
    }
}
