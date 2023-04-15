package frendit.xyz.com.controller;

import frendit.xyz.com.model.post.CreatePost;
import frendit.xyz.com.model.post.PostFrequency;
import frendit.xyz.com.service.AuthService;
import frendit.xyz.com.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost (@RequestBody CreatePost createPost) throws Exception {
        String email = authService.getEmailOfLoggedUser();
        PostFrequency postFrequency = postService.countPostFrequency(email);
        if(postFrequency.getDaily_post_count() >= 5){
            throw new Exception("You have already posted 5 times today.");
        }
        if(postFrequency.getLast_post_ago() <= 10){
            throw new Exception("You already posted " + postFrequency.getLast_post_ago() + " minutes ago.");
        }
        createPost.setEmail(email);
        try{
            int count = postService.insertPost(createPost);
            if(count == 0) throw new  Exception("Post couldn't be created with provided data");
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch(Exception e){
            throw new Exception("Post couldn't be created with provided data");
        }
    }
}