package net.kkennib.house.controllers;

import lombok.RequiredArgsConstructor;
import net.kkennib.house.models.Post;
import net.kkennib.house.models.PostResponse;
import net.kkennib.house.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/post/{articleType}/{postId}")
    public Mono<ResponseEntity<Post>> getCustomerById(
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
        Mono<PostResponse> res = postService.getPosts(articleType, pageNo);
        return res.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
