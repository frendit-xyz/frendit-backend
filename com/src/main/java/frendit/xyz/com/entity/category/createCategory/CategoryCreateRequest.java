package frendit.xyz.com.entity.category.createCategory;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;


// will be moved to model folder later
@Data
public class CategoryCreateRequest {
    @Nullable
    private String slug;
    @NotNull
    private String title;
}
