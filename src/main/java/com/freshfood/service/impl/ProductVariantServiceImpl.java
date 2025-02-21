package com.freshfood.service.impl;

import com.freshfood.dto.request.ProductVariantRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.model.ProductVariant;
import com.freshfood.repository.ProductVariantRepository;
import com.freshfood.service.ProductService;
import com.freshfood.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductService productService;

    @Override
    public int addProductVariant(ProductVariantRequestDTO productVariantRequestDTO, String thumbnailUrl) {
        ProductVariant productVariant = ProductVariant.builder()
                .name(productVariantRequestDTO.getName())
                .thumbnailUrl(thumbnailUrl)
                .price(productVariantRequestDTO.getPrice())
                .product(productService.getProduct(productVariantRequestDTO.getProductId()))
                .discountPercentage(productVariantRequestDTO.getDiscountPercentage())
                .expiryDate(productVariantRequestDTO.getExpiryDate())
                .status(productVariantRequestDTO.getStatus())
                .unit(productVariantRequestDTO.getUnit())
                .build();
        productVariantRepository.save(productVariant);
        return productVariant.getId();
    }

    @Override
    public void updateProductVariant(int id, ProductVariantRequestDTO productVariantRequestDTO, String thumbnailUrl) {
        ProductVariant productVariant = getProductVariantById(id);
        productVariant.setName(productVariantRequestDTO.getName());
        productVariant.setPrice(productVariantRequestDTO.getPrice());
        productVariant.setStatus(productVariantRequestDTO.getStatus());
        productVariant.setUnit(productVariantRequestDTO.getUnit());
        productVariant.setExpiryDate(productVariantRequestDTO.getExpiryDate());
        productVariant.setDiscountPercentage(productVariantRequestDTO.getDiscountPercentage());
        productVariant.setThumbnailUrl(thumbnailUrl);
        productVariant.setProduct(productService.getProduct(productVariantRequestDTO.getProductId()));
        productVariantRepository.save(productVariant);
    }

    @Override
    public void deleteProductVariant(int id) {
        productVariantRepository.deleteById(id);
    }

    @Override
    public ProductVariant getProductVariantById(int id) {
        return productVariantRepository.findById(id).orElse(null);
    }

    @Override
    public PageResponse getAllProductVariants() {
        return null;
    }
}
