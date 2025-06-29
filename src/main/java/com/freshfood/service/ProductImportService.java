package com.freshfood.service;

import com.freshfood.dto.request.ProductImportRequestDTO;
import com.freshfood.dto.response.PageResponse;

public interface ProductImportService {
    void createProductImport(ProductImportRequestDTO productImportRequestDTO);
    PageResponse getProductInventory(int pageNo, int pageSize);

}
