package frendit.xyz.com.service;

import frendit.xyz.com.model.post.CreatePost;
import frendit.xyz.com.model.post.PostFrequency;
import frendit.xyz.com.repository.postgres.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public int insertPost (CreatePost createPost) {
        return postRepository.insertPost(createPost);
    }

    public PostFrequency countPostFrequency (String email) {
        return postRepository.countPostFrequency(email);
    }
}