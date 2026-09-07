package com.fmhyclone.service;

import com.fmhyclone.dto.CategoryResponse;
import com.fmhyclone.entity.Category;
import com.fmhyclone.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getIcon(),
            category.getSlug(),
            category.getDisplayOrder()
        );
    }
}