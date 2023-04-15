package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.model.post.CreatePostEntity;
import frendit.xyz.com.model.post.PostFrequency;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostRepository {
    void insertPost(CreatePostEntity createPostEntity);

    PostFrequency countPostFrequency(String email);
}
