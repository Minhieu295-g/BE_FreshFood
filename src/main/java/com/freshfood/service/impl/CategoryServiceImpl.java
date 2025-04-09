package com.freshfood.service.impl;

import com.freshfood.dto.request.CategoryRequestDTO;
import com.freshfood.dto.response.CategoryResponseDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.model.Category;
import com.freshfood.repository.CategoryRepository;
import com.freshfood.service.CategoryService;
import com.freshfood.service.ParentCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final ParentCategoryService parentCategoryService;
    private final CategoryRepository categoryRepository;
    @Override
    public int saveCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = Category.builder()
                .name(categoryRequestDTO.getName())
                .parentCategory(parentCategoryService.getParentCategoryById(categoryRequestDTO.getParentCategoryId()))
                .build();
        categoryRepository.save(category);
        return category.getId();
    }

    @Override
    public void updateCategory(int id, CategoryRequestDTO categoryRequestDTO) {
        Category category = getCategoryById(id);
        category.setName(categoryRequestDTO.getName());
        category.setParentCategory(parentCategoryService.getParentCategoryById(categoryRequestDTO.getParentCategoryId()));
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(int id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            log.info("Delete Category : {}", category.getName());
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete category. It is referenced by other entities.");
        }
    }


    @Override
    public PageResponse getAllCategory(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryResponseDTO> categoryResponseDTOS = categories.stream().map(category -> CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build()).toList();
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(categories.getTotalPages())
                .items(categoryResponseDTOS)
                .build();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(null);
    }
}
