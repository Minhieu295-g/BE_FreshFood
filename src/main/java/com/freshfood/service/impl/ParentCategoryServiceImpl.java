package com.freshfood.service.impl;

import com.freshfood.dto.request.ParentCategoryRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.model.ParentCategory;
import com.freshfood.repository.ParentCategoryRepository;
import com.freshfood.service.ParentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParentCategoryServiceImpl implements ParentCategoryService {
    private final ParentCategoryRepository parentCategoryRepository;
    @Override
    public int saveParentCategory(ParentCategoryRequestDTO parentCategoryRequestDTO, String imageUrl) {
        ParentCategory parentCategory = ParentCategory.builder()
                .name(parentCategoryRequestDTO.getName())
                .imageUrl(imageUrl).build();

        parentCategoryRepository.save(parentCategory);
        return parentCategory.getId();
    }

    @Override
    public void updateParentCategory(int id, ParentCategoryRequestDTO parentCategoryRequestDTO, String imageUrl) {

    }

    @Override
    public void deleteParentCategory(int id) {

    }

    @Override
    public PageResponse getParentCategory(int pageNo, int pageSize) {
        return null;
    }
}
