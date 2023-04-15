package frendit.xyz.com.controller;

import frendit.xyz.com.helpers.Respond;
import frendit.xyz.com.model.post.CreatePostEntity;
import frendit.xyz.com.model.post.CreatePostForm;
import frendit.xyz.com.service.AuthService;
import frendit.xyz.com.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    private final Respond respond;

    public PostController(PostService postService, AuthService authService, Respond respond) {
        this.postService = postService;
        this.respond = respond;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPost (@RequestBody CreatePostForm createPostForm) throws Exception {
        CreatePostEntity createPostEntity = postService.validateBeforeCreate(createPostForm);
        try{
            postService.insertPost(createPostEntity);
            return respond.sendResponse(HttpStatus.CREATED, "Post created successfully");
        } catch(Exception e){
            throw new Exception("Post couldn't be created with provided data");
        }
    }
}
