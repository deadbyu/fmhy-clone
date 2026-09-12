package com.fmhyclone.service;

import com.fmhyclone.dto.CategoryRequest;
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

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .slug(request.slug())
                .description(request.description())
                .icon(request.icon())
                .displayOrder(request.displayOrder())
                .build();

        return mapToResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        category.setName(request.name());
        category.setSlug(request.slug());
        category.setDescription(request.description());
        category.setIcon(request.icon());
        category.setDisplayOrder(request.displayOrder());

        return mapToResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
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