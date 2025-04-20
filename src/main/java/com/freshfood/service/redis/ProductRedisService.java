package com.freshfood.service.redis;

import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRedisService {
    PageResponse getAllProducts(Pageable pageable, String[] product, String[] category, String[] productVariant);
    void saveAllProducts(PageResponse<List<ProductResponseDTO>> pageResponse, Pageable pageable, String[] product, String[] category, String[] productVariant);
    void clear();
}
