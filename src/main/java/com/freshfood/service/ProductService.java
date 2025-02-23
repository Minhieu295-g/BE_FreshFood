package com.freshfood.service;

import com.freshfood.dto.request.ProductRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.ProductResponseDTO;
import com.freshfood.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    int addProduct(ProductRequestDTO productRequestDTO, String thumbnailUrl , String[] imageUrl);
    void updateProduct(int id,ProductRequestDTO productRequestDTO, String thumbnailUrl, String[] imageUrl);
    void deleteProduct(int id);
    Product getProduct(int id);
    ProductResponseDTO getProductResponseDTO(int id);
    PageResponse getProducts(int pageNo, int pageSize);
    PageResponse getProductDefaultWithSearchAndSearch(int pageNo, int pageSize, String sort, String search);
    PageResponse advanceSearchWithSpecification(Pageable pageable, String[] product, String[] category, String[] productVariant);
}
