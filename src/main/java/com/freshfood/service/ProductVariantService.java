package com.freshfood.service;

import com.freshfood.dto.request.ProductVariantRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.ProductVariantResponseDTO;
import com.freshfood.model.ProductVariant;

public interface ProductVariantService {
    int addProductVariant(ProductVariantRequestDTO productVariantRequestDTO, String thumbnailUrl);
    void updateProductVariant(int id,ProductVariantRequestDTO productVariantRequestDTO, String thumbnailUrl);
    void deleteProductVariant(int id);
    ProductVariant getProductVariantById(int id);
    PageResponse getAllProductVariants();
}
