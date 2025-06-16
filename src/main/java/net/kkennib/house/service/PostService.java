package net.kkennib.house.service;

import lombok.RequiredArgsConstructor;
import net.kkennib.house.model.Post;
import net.kkennib.house.model.PostResponse;
import net.kkennib.house.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Mono<Post> getPostById(String articleType, int postId) {
        return Mono.just(postRepository.getPostById(articleType, postId));
    }

    public Mono<PostResponse> getPosts(String articleType, int pageNo) {
        return Mono.just(postRepository.getPosts(articleType, pageNo));
    }

    public Mono<PostResponse> getPostsFromLocal(String articleType, int pageNo) {
        return Mono.just(postRepository.getPostsFromLocal(articleType, pageNo));
    }


    public Mono<PostResponse> createPost(Post post) {

        List<Post> postList = new ArrayList<>(Arrays.asList(post));
        return Mono.just(postRepository.createPosts(postList));
    }
}
