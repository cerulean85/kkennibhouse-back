package net.kkennib.house.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.kkennib.house.models.Post;
import net.kkennib.house.models.PostResponse;
import net.kkennib.house.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/post/{articleType}/{postId}")
    public Mono<ResponseEntity<Post>> getPostList(
            @PathVariable("articleType") String articleType,
            @PathVariable("postId") String postId) {

        Mono<Post> res = postService.getPostById(articleType, postId);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/posts/{articleType}/{pageNo}")
    public Mono<ResponseEntity<PostResponse>> getPosts(
            @PathVariable("articleType") String articleType,
            @PathVariable("pageNo") int pageNo)
    {
        Mono<PostResponse> res = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String url = request.getRequestURL().toString();
        if (url.contains("localhost")) {
            res = postService.getPostsFromLocal(articleType, pageNo);
        } else {
            res = postService.getPosts(articleType, pageNo);
        }
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/post")
    Mono<ResponseEntity<PostResponse>> createPost(@RequestBody Post post)
    {
        Mono<PostResponse> res = postService.createPost(post);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<PostResponse>> getPosts()
    {
        Mono<PostResponse> res = postService.getPosts("about", 1);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello! This is Spring Boot + AWS Lambda demo project.";
    }
}
