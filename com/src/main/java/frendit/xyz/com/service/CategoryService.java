package frendit.xyz.com.service;

import frendit.xyz.com.entity.category.createCategory.CategoryCreateRequest;
import frendit.xyz.com.repository.postgres.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void CreateCategory(CategoryCreateRequest categoryCreateRequest) {
        categoryRepository.createCategory(categoryCreateRequest);
    }
}
