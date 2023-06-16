package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.entity.category.createCategory.CategoryCreateRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRepository {
    void createCategory(CategoryCreateRequest categoryCreateRequest);
}
