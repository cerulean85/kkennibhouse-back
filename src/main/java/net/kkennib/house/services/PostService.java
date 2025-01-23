package net.kkennib.house.services;

import lombok.RequiredArgsConstructor;
import net.kkennib.house.models.Post;
import net.kkennib.house.models.PostResponse;
import net.kkennib.house.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Mono<Post> getPostById(String articleType, String postId) {
        return Mono.just(postRepository.getPostById(articleType, postId));
    }

    public Mono<PostResponse> getPosts(String articleType, int pageNo) {
        return Mono.just(postRepository.getPosts(articleType, pageNo));
    }
}
