package frendit.xyz.com.repository.postgres;

import frendit.xyz.com.model.category.CreateCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRepository {
    void createCategory(CreateCategoryEntity createCategoryEntity);
}
