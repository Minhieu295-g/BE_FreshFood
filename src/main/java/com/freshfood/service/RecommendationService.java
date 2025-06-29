package com.freshfood.service;

import com.freshfood.dto.response.*;
import com.freshfood.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ProductService productService;
    public RecommendationResponse getRecommendations(int userId, int topN) {
        String url = String.format("https://8150-34-106-67-175.ngrok-free.app//recommend?user_id=%d&top_n=%d", userId, topN);
        return restTemplate.getForObject(url, RecommendationResponse.class);
    }

    public PageResponse getProductRecommendations(int pageNo, int pageSize, int userId, int topN){
        List<Product> recommendedProducts = getRecommendedProducts(userId, topN);
        List<ProductResponseDTO> productResponses = recommendedProducts.stream().map(product -> ProductResponseDTO.builder()
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
                .totalPage(10)
                .items(convertToDefaultProduct(productResponses))
                .build();
    }
    public List<Product> getRecommendedProducts(int userId, int topN) {
        RecommendationResponse recommend = getRecommendations(userId, topN);

        List<Integer> productIds = recommend.getRecommendations().stream()
                .map(r -> (Integer) r.get(0))         // Lấy productId
                .filter(id -> id > 5)                 // Lọc productId > 5
                .map(id -> id + 20)                   // Cộng thêm 20
                .collect(Collectors.toList());


        return productIds.stream()
                .map(productService::getProduct)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public List<DefaultProduct> convertToDefaultProduct(List<ProductResponseDTO> productResponseDTOS){
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
