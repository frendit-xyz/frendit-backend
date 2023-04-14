package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.model.post.CreatePost;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostRepository {
    int insertPost(CreatePost createPost);

    int countDailyPosts(String email);

    int countPostedMinutesAgo(String email);
}
