package com.freshfood.service.impl;

import com.freshfood.dto.request.ProductRequestDTO;
import com.freshfood.dto.request.ProductVariantRequestDTO;
import com.freshfood.dto.response.*;
import com.freshfood.model.Product;
import com.freshfood.model.ProductImage;
import com.freshfood.repository.CategoryRepository;
import com.freshfood.repository.ProductRepository;
import com.freshfood.repository.search.ProductSearchRepository;
import com.freshfood.service.CategoryService;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductSearchRepository productSearchRepository;
    @Override
    public int addProduct(ProductRequestDTO productRequestDTO, String thumbnailUrl, String[] imageUrl) {
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .category(categoryService.getCategoryById(productRequestDTO.getCategoryId()))
                .thumbnailUrl(thumbnailUrl)
                .build();
        product.setProductImages(convertToProductImage(imageUrl, product));
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public void updateProduct(int id, ProductRequestDTO productRequestDTO, String thumbnailUrl, String[] imageUrl) {
        Product product = getProduct(id);
        product.setProductImages(convertToProductImage(imageUrl, product));
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setCategory(categoryService.getCategoryById(productRequestDTO.getCategoryId()));
        product.setThumbnailUrl(thumbnailUrl);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductResponseDTO getProductResponseDTO(int id) {
        Product product = getProduct(id);
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .category(CategoryResponseDTO.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build())
                .productImages((HashSet<ProductImageResponseDTO>) product.getProductImages().stream().map(image -> ProductImageResponseDTO.builder()
                        .id(image.getId())
                        .altText(image.getAltText())
                        .imageUrl(image.getImageUrl())
                        .build()).collect(Collectors.toSet()))
                .productVariants((HashSet<ProductVariantResponseDTO>) product.getProductVariants().stream().map(variant -> ProductVariantResponseDTO.builder()
                        .id(variant.getId())
                        .name(variant.getName())
                        .price(variant.getPrice())
                        .unit(variant.getUnit().toString())
                        .expiryDate(variant.getExpiryDate())
                        .status(variant.getStatus().toString())
                        .discountPercentage(variant.getDiscountPercentage())
                        .thumbnailUrl(variant.getThumbnailUrl())
                        .build()).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public PageResponse getProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductResponseDTO> productResponseDTOS = products.stream().map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .category(CategoryResponseDTO.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build())
                .productImages((HashSet<ProductImageResponseDTO>) product.getProductImages().stream().map(image -> ProductImageResponseDTO.builder()
                        .id(image.getId())
                        .altText(image.getAltText())
                        .imageUrl(image.getImageUrl())
                        .build()).collect(Collectors.toSet()))
                .productVariants((HashSet<ProductVariantResponseDTO>) product.getProductVariants().stream().map(variant -> ProductVariantResponseDTO.builder()
                        .id(variant.getId())
                        .name(variant.getName())
                        .price(variant.getPrice())
                        .unit(variant.getUnit().toString())
                        .expiryDate(variant.getExpiryDate())
                        .status(variant.getStatus().toString())
                        .discountPercentage(variant.getDiscountPercentage())
                        .thumbnailUrl(variant.getThumbnailUrl())
                        .build()).collect(Collectors.toSet()))
                .build()).toList();
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(products.getTotalPages())
                .items(convertToDefaultProduct(productResponseDTOS))
                .build();
    }

    @Override
    public PageResponse getProductDefaultWithSearchAndSearch(int pageNo, int pageSize, String sort, String search) {
        return productSearchRepository.getAllDefaultProductsWithSortAndSearch(pageNo, pageSize, sort, search);
    }

    @Override
    public PageResponse advanceSearchWithSpecification(Pageable pageable, String[] product, String[] category, String[] productVariant) {
        return productSearchRepository.advanceSearchWithSpecification(pageable,product,category, productVariant);
    }

    @Override
    public PageResponse advanceSearchProductVariantWithSpecification(Pageable pageable, String[] product, String[] category, String[] productVariant) {
        PageResponse pageResponse = productSearchRepository.advanceSearchWithSpecification(pageable,product,category, productVariant);
        pageResponse.setItems(convertToDefaultProduct((List<ProductResponseDTO>) pageResponse.getItems()));
        return pageResponse;
    }


    private HashSet<ProductImage> convertToProductImage(String[] urlImage, Product product) {
        HashSet<ProductImage> productImages = new HashSet<>();
        for (int i = 0; i < urlImage.length; i++) {
            productImages.add(ProductImage.builder()
                    .imageUrl(urlImage[i])
                    .altText("products")
                    .product(product)
                    .build());
        }
        return productImages;
    }
    private List<DefaultProduct> convertToDefaultProduct(List<ProductResponseDTO> productResponseDTOS){
        List<DefaultProduct> defaultProducts = new ArrayList<>();
        for (ProductResponseDTO productResponseDTO : productResponseDTOS) {
            if(!productResponseDTO.getProductVariants().isEmpty()){
                ProductVariantResponseDTO variant = productResponseDTO.getProductVariants()
                        .stream()
                        .findFirst()
                        .map(v -> ProductVariantResponseDTO.builder()
                                .thumbnailUrl(v.getThumbnailUrl())
                                .price(v.getPrice())
                                .unit(v.getUnit())
                                .name(v.getName())
                                .id(v.getId())
                                .discountPercentage(v.getDiscountPercentage())
                                .expiryDate(v.getExpiryDate())
                                .status(v.getStatus())
                                .build())
                        .orElse(null);
                DefaultProduct defaultProduct = DefaultProduct.builder()
                        .id(productResponseDTO.getId())
                        .productVariantId(variant.getId())
                        .thumbnailUrl(productResponseDTO.getThumbnailUrl())
                        .price(variant.getPrice())
                        .discountPercentage(variant.getDiscountPercentage())
                        .name(productResponseDTO.getName())
                        .build();
                defaultProducts.add(defaultProduct);
            }
        }
        return defaultProducts;
    }
}
