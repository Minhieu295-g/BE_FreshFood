package com.freshfood.controller;

import com.freshfood.dto.request.ParentCategoryRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ParentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parent-category")
@Validated
public class ParentCategoryController {
    private final ParentCategoryService parentCategoryService;
    private final CloudinaryService cloudinaryService;
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> addParentCategory(
            @RequestPart("file") MultipartFile file, @RequestPart("parentCategory") String name) throws IOException {
        ParentCategoryRequestDTO parentCategoryRequestDTO = new ParentCategoryRequestDTO();
        parentCategoryRequestDTO.setName(name);
        int id = parentCategoryService.saveParentCategory(parentCategoryRequestDTO, cloudinaryService.uploadImage(file));
        return new ResponseData<>(HttpStatus.CREATED.value(), "Parent category added successfully", id );
    }
}
