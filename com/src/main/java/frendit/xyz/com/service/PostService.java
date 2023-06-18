package frendit.xyz.com.service;

import frendit.xyz.com.model.post.CreatePostEntity;
import frendit.xyz.com.model.post.CreatePostForm;
import frendit.xyz.com.model.post.PostFrequency;
import frendit.xyz.com.repository.postgres.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final AuthService authService;

    private final ContentQualityService contentQualityService;

    private final PostRepository postRepository;

    public PostService(AuthService authService, ContentQualityService contentQualityService, PostRepository postRepository) {
        this.authService = authService;
        this.contentQualityService = contentQualityService;
        this.postRepository = postRepository;
    }

    public void insertPost (CreatePostEntity createPostEntity) {
        postRepository.insertPost(createPostEntity);
    }

    public PostFrequency countPostFrequency (String email) {
        return postRepository.countPostFrequency(email);
    }

    @Transactional
    public CreatePostEntity validateBeforeCreate(CreatePostForm createPostForm) throws Exception {
        String email = authService.getEmailOfLoggedUser();
        PostFrequency postFrequency = postRepository.countPostFrequency(email);
        if(postFrequency.getDaily_post_count() >= 5){
            throw new Exception("You have already posted " + postFrequency.getDaily_post_count() +" times today.");
        }
        if(postFrequency.getLast_post_ago() != -1 && postFrequency.getLast_post_ago() <= 10){
            throw new Exception("You already posted " + postFrequency.getLast_post_ago() + " minutes ago.");
        }
        CreatePostEntity createPostEntity = new CreatePostEntity();

        if(createPostForm.getRich() != null){
            createPostEntity.setContent(createPostForm.getRich().getContent());
            createPostEntity.setVideo_link(createPostForm.getRich().getVideo_link());
            createPostEntity.setGif_link(createPostForm.getRich().getGif_link());
            createPostEntity.setLinks(createPostForm.getRich().getLinks());
            createPostEntity.setTags(contentQualityService.extractTopics(createPostForm.getRich().getContent()));
        }

        if(createPostForm.getSimple() != null){
            createPostEntity.setBg_text(createPostForm.getSimple().getBg_text());
            createPostEntity.setBg_color(createPostForm.getSimple().getBg_color());
            createPostEntity.setTags(contentQualityService.extractTopics(createPostForm.getSimple().getBg_text()));
        }

        createPostEntity.setPublish_at(createPostForm.getPublish_at());
        createPostEntity.setActivity_details(createPostForm.getActivity_details());
        createPostEntity.setStatus(createPostForm.getStatus());

        createPostEntity.setEmail(email);
        return createPostEntity;
    }
}
