package frendit.xyz.com.model.post;

import frendit.xyz.com.enums.PostStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CreatePostForm {
    private SimpleType simple;
    private RichType rich;
    @Data
    public static class RichType {
        private String content;
        private String video_link;
        private String gif_link;
        private String links;

    }
    @Data
    public static class SimpleType {
        private String bg_text;
        private String bg_color;
    }
    private String publish_at;
    private String activity_details;
    private PostStatus status;
}
