package frendit.xyz.com.entity.post;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PostEntity {
    private Integer id;
    private String content;
    private String bg_text;
    private String bg_color;
    private String video_link;
    private String gif_link;
    private String links;
    private List<String> _links;
    private Timestamp publish_at;
    private String activity_details;
    private String status;

    private boolean can_react;
    private boolean can_comment;
    private boolean can_vote;
    private Integer boost;

    private Integer activity_id;
    private Integer author_id;
    private Integer location_id;
    private Integer mood_id;
    private Integer category_id;

    private Timestamp created_at;
    private Timestamp updated_at;
}
