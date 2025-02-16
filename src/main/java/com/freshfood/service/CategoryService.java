package com.freshfood.service;

import com.freshfood.dto.request.CategoryRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.model.Category;

public interface CategoryService {
    int saveCategory(CategoryRequestDTO categoryRequestDTO);
    void updateCategory(int id, CategoryRequestDTO categoryRequestDTO);
    void deleteCategory(int id);
    PageResponse getAllCategory(int pageNo, int pageSize);
    Category getCategoryById(int id);
}
