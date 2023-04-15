package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.model.post.CreatePost;
import frendit.xyz.com.model.post.PostFrequency;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostRepository {
    int insertPost(CreatePost createPost);

    PostFrequency countPostFrequency(String email);
}
