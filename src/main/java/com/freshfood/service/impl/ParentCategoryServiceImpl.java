package com.freshfood.service.impl;

import com.freshfood.dto.request.ParentCategoryRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.ParentCategoryResponseDTO;
import com.freshfood.model.ParentCategory;
import com.freshfood.repository.ParentCategoryRepository;
import com.freshfood.service.ParentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        ParentCategory parentCategory = parentCategoryRepository.findById(id).get();
        parentCategory.setName(parentCategoryRequestDTO.getName());
        if(imageUrl != null) {
            parentCategory.setImageUrl(imageUrl);
        }
        parentCategoryRepository.save(parentCategory);
    }

    @Override
    public void deleteParentCategory(int id) {
        parentCategoryRepository.deleteById(id);
    }

    @Override
    public PageResponse getParentCategory(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ParentCategory> parentCategories = parentCategoryRepository.findAll(pageable);
        List<ParentCategoryResponseDTO> parentCategoryResponseDTOS = parentCategories.stream().map(parent -> ParentCategoryResponseDTO.builder()
                .id(parent.getId())
                .name(parent.getName())
                .imageUrl(parent.getImageUrl())
                .categories(parent.getCategories())
                .build()).toList();
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(parentCategories.getTotalPages())
                .items(parentCategoryResponseDTOS)
                .build();
    }

    @Override
    public ParentCategory getParentCategoryById(int id) {
        return parentCategoryRepository.findById(id).orElseThrow(() -> null);
    }

}
