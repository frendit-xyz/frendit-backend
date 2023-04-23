package frendit.xyz.com.entity.post;

import frendit.xyz.com.enums.PostStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostEntity {
    private Integer id;

    private String content;
    private String bg_text;
    private String bg_color;
    private String video_link;
    private String gif_link;
    @Getter(AccessLevel.NONE)
    private String links;
    private List<String> _links;
    @Getter(AccessLevel.NONE)
    private String tags;
    private List<String> _tags;
    private Timestamp publish_at;
    private String activity_details;
    private PostStatus status;

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

    public List<String> get_links() {
        if (links == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(links.replaceAll("[{}]", "").split(","))
                .filter(e -> !e.equalsIgnoreCase("NULL"))
                .distinct()
                .collect(Collectors.toList());

    }

    public List<String> get_tags() {
        if (tags == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(tags.replaceAll("[{}]", "").split(","))
                .filter(e -> !e.equalsIgnoreCase("NULL"))
                .distinct()
                .collect(Collectors.toList());

    }
}
