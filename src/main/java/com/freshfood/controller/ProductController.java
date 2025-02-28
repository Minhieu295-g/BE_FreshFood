package com.freshfood.controller;

import com.freshfood.dto.request.ProductRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> addProduct(@RequestPart("thumbnail") MultipartFile thumbnail , @RequestPart("images") MultipartFile[] images, @Valid @RequestPart("product") ProductRequestDTO productRequestDTO) throws IOException {
        productService.addProduct(productRequestDTO, cloudinaryService.uploadImage(thumbnail) ,cloudinaryService.uploadArrImage(images));
        return new ResponseData<>(HttpStatus.OK.value(), "Product added successfully");
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> updateProduct(@PathVariable int id, @RequestPart("thumbnail") MultipartFile thumbnail , @RequestPart("images") MultipartFile[] images, @Valid @RequestPart("product") ProductRequestDTO productRequestDTO) throws IOException {
        productService.updateProduct(id,productRequestDTO, cloudinaryService.uploadImage(thumbnail) ,cloudinaryService.uploadArrImage(images));
        return new ResponseData<>(HttpStatus.OK.value(), "Product updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseData<?> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Product deleted successfully");
    }
    @GetMapping("/{id}")
    public ResponseData<?> getProductById(@PathVariable int id) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get product successfully", productService.getProductResponseDTO(id));
    }
    @GetMapping("/list")
    public ResponseData<?> getProducts(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list products successfully", productService.getProducts(pageNo,pageSize));
    }

    @GetMapping("/list-default")
    public ResponseData<?> getProductDefaultWithSearchAndSort(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String sort, @RequestParam(required = false) String search) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list products default successfully", productService.getProductDefaultWithSearchAndSearch(pageNo,pageSize,sort,search));
    }
    @GetMapping("/list-with-search")
    public ResponseData<?> getAllProductsWithSpecification(Pageable pageable, @RequestParam(required = false) String[] product, @RequestParam(required = false) String[] category, @RequestParam(required = false) String[] productVariant) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list products success", productService.advanceSearchWithSpecification(pageable, product,category ,productVariant));
    }
    @GetMapping("/list-with-search-product")
    public ResponseData<?> getAllProductDefaultWithSpecification(Pageable pageable, @RequestParam(required = false) String[] product, @RequestParam(required = false) String[] category, @RequestParam(required = false) String[] productVariant) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list products success", productService.advanceSearchProductVariantWithSpecification(pageable, product,category ,productVariant));
    }


}
