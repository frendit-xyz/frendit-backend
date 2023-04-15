package frendit.xyz.com.model.post;

import frendit.xyz.com.enums.PostStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreatePostEntity {
    private String content;
    private String bg_text;
    private String bg_color;
    private String video_link;
    private String gif_link;
    private String links;
    private String publish_at;
    private String activity_details;
    private PostStatus status;

    private String email;
}
