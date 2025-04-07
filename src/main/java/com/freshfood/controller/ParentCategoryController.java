package com.freshfood.controller;

import com.freshfood.dto.request.ParentCategoryRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ParentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
            @RequestPart(value = "file", required = false) MultipartFile file, @RequestPart("name") String name) throws IOException {
        ParentCategoryRequestDTO parentCategoryRequestDTO = new ParentCategoryRequestDTO();
        parentCategoryRequestDTO.setName(name);
        int id = parentCategoryService.saveParentCategory(parentCategoryRequestDTO, cloudinaryService.uploadImage(file));
        return new ResponseData<>(HttpStatus.CREATED.value(), "Parent category added successfully", id );
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> updateParentCategory(@PathVariable int id,@RequestPart("file") MultipartFile file,@RequestPart("name") String name) throws IOException {
        parentCategoryService.updateParentCategory(id, new ParentCategoryRequestDTO(name), cloudinaryService.uploadImage(file));
        return new ResponseData<>(HttpStatus.CREATED.value(), "Parent category was updated successfully");
    }
    @DeleteMapping(value = "/{id}")
    public ResponseData<?> deleteParentCategory(@PathVariable int id){
        parentCategoryService.deleteParentCategory(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Parent category was deleted successfully");
    }
    @GetMapping(value = "/list")
    public ResponseData<?> getAllParentCategory(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseData<>(HttpStatus.OK.value(), "Get list all parent category successfully", parentCategoryService.getParentCategory(pageNo,pageSize));
    }


}
