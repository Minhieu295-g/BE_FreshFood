package com.freshfood.service.impl;

import com.freshfood.dto.request.ProductImportRequestDTO;
import com.freshfood.dto.response.PageResponse;
import com.freshfood.dto.response.ProductInventoryResponse;
import com.freshfood.dto.response.ProductVariantResponseDTO;
import com.freshfood.model.ProductImport;
import com.freshfood.model.ProductVariant;
import com.freshfood.repository.ProductImportRepository;
import com.freshfood.service.ProductImportService;
import com.freshfood.service.ProductVariantService;
import com.freshfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImportImpl implements ProductImportService {
    private final ProductImportRepository productImportRepository;
    private final ProductVariantService productVariantService;
    private final UserService userService;

    @Override
    public void createProductImport(ProductImportRequestDTO productImportRequestDTO) {
        ProductImport productImport = ProductImport.builder()
                .productVariant(productVariantService.getProductVariantById(productImportRequestDTO.getProductVariantId()))
                .user(userService.findByUserId(productImportRequestDTO.getUserId()))
                .quantity(productImportRequestDTO.getQuantity())
                .build();
        productImportRepository.save(productImport);
    }

    @Override
    public PageResponse getProductInventory(int pageNo, int pageSize) {
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(10)
                .items(getProductInventories())
                .build();
    }

    public List<ProductInventoryResponse> getProductInventories() {
        List<Object[]> results = productImportRepository.getProductInventoryRaw();

        return results.stream().map(row -> {
            int id = ((Number) row[0]).intValue();
            String name = (String) row[1];
            int totalImport = ((Number) row[2]).intValue();
            int totalSold = ((Number) row[3]).intValue();
            int stock = ((Number) row[4]).intValue();
            ProductVariant productVariant = productVariantService.getProductVariantById(id);
            ProductVariantResponseDTO productVariantResponseDTO = ProductVariantResponseDTO.builder()
                    .id(productVariant.getId())
                    .name(productVariant.getName())
                    .price(productVariant.getPrice())
                    .unit(productVariant.getUnit().toString())
                    .thumbnailUrl(productVariant.getThumbnailUrl())
                    .status(productVariant.getStatus().toString())
                    .discountPercentage(productVariant.getDiscountPercentage())
                    .build();
            return new ProductInventoryResponse(productVariantResponseDTO, totalImport, totalSold, stock);
        }).collect(Collectors.toList());
    }

}
