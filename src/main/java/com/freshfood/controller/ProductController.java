package com.freshfood.controller;

import com.freshfood.dto.request.ProductRequestDTO;
import com.freshfood.dto.request.ProductVariantRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ProductService;
import com.freshfood.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final ProductVariantService productVariantService;

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

    @PostMapping(value = "/product-with-variant/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<?> addProductWithVariants(
            @RequestPart(value = "thumbnail", required = false) MultipartFile productThumbnail,
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @Valid @RequestPart("product") ProductRequestDTO productRequestDTO,
            @RequestPart(value = "variants", required = false) MultipartFile[] variantThumbnails,
            @Valid @RequestPart(value = "product_variants", required = false) ProductVariantRequestDTO[] productVariantRequestDTOs
    ) throws IOException {
        try {
            String productThumbnailUrl = cloudinaryService.uploadImage(productThumbnail);
            List<String> imageUrls = images != null ? List.of(cloudinaryService.uploadArrImage(images)) : new ArrayList<>();
            int productId = productService.addProduct(productRequestDTO, productThumbnailUrl, imageUrls.toArray(new String[0]));

            List<Integer> variantIds = new ArrayList<>();
            for (int i = 0; i < productVariantRequestDTOs.length; i++) {
                ProductVariantRequestDTO variantDTO = productVariantRequestDTOs[i];
                MultipartFile variantThumbnail = variantThumbnails[i];
                String variantThumbnailUrl = cloudinaryService.uploadImage(images[0]);
                variantDTO.setProductId(productId);
                int variantId = productVariantService.addProductVariant(variantDTO, variantThumbnailUrl);
                variantIds.add(variantId);
            }
            return new ResponseData<>(HttpStatus.OK.value(), "Product and variants added successfully");
        } catch (Exception ex) {
            throw ex;
        }
    }
    @PostMapping("/met-vcl")
    public ResponseData<?> addProductVariant() {
        return new ResponseData<>(HttpStatus.OK.value(), "Product added successfully");
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
