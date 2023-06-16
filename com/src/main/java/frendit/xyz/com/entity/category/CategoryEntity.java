package frendit.xyz.com.entity.category;

import lombok.Data;

import javax.annotation.Nullable;
import java.sql.Timestamp;

@Data
public class CategoryEntity {
    private int id;
    @Nullable
    private String slug;
    private String title;
    private Timestamp created_at;
    private Timestamp updated_at;
}
