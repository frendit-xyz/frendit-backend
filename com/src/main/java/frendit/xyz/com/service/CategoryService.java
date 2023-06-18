package frendit.xyz.com.service;

import frendit.xyz.com.model.category.CreateCategoryEntity;
import frendit.xyz.com.repository.postgres.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void CreateCategory(CreateCategoryEntity createCategoryEntity) {
        categoryRepository.createCategory(createCategoryEntity);
    }
}
