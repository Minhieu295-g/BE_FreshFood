package com.freshfood.controller;

import com.freshfood.dto.request.CategoryRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/")
    public ResponseData<?> addCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Category was created successfully", categoryService.saveCategory(categoryRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseData<?> updateCategory(@PathVariable int id, @RequestBody CategoryRequestDTO categoryRequestDTO){
        categoryService.updateCategory(id, categoryRequestDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "Category was updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseData<?> deleteCategory(@PathVariable int id){
        categoryService.deleteCategory(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Category was deleted successfully");
    }
    @GetMapping("/list")
    public ResponseData<?> getAllCategory(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "20") int pageSize){
        return new ResponseData<>(HttpStatus.OK.value(), "Get list category successfully", categoryService.getAllCategory(pageNo, pageSize));
    }
}
