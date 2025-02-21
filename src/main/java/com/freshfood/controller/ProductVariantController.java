package com.freshfood.controller;

import com.freshfood.dto.request.ProductRequestDTO;
import com.freshfood.dto.request.ProductVariantRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product-variant")
@RequiredArgsConstructor
@Valid
public class ProductVariantController {
    private final ProductVariantService productVariantService;
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> addProductVariant(@RequestPart("thumbnail") MultipartFile thumbnail, @Valid @RequestPart("product") ProductVariantRequestDTO productVariantRequestDTO) throws IOException {
        int id = productVariantService.addProductVariant(productVariantRequestDTO, cloudinaryService.uploadImage(thumbnail));
        return new ResponseData<>(HttpStatus.OK.value(), "Product was added successfully", id);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> updateProductVariant(@PathVariable int id, @RequestPart("thumbnail") MultipartFile thumbnail, @Valid @RequestPart("product") ProductVariantRequestDTO productVariantRequestDTO) throws IOException {
        productVariantService.updateProductVariant(id, productVariantRequestDTO, cloudinaryService.uploadImage(thumbnail));
        return new ResponseData<>(HttpStatus.OK.value(), "Product was updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseData<?> deleteProductVariant(@PathVariable int id) {
        productVariantService.deleteProductVariant(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Product was deleted successfully");
    }
}
