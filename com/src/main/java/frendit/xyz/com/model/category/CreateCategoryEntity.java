package frendit.xyz.com.model.category;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateCategoryEntity {
    @NotBlank(message = "Slug is mandatory")
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug should be of slug format")
    private String slug;
    @NotNull(message = "Title is required")
    private String title;
}
