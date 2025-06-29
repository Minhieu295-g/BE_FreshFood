package com.freshfood.controller;

import com.freshfood.dto.request.ProductImportRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/product-import")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductImportController {
    private final ProductImportService productImportService;

    @GetMapping("/inventory")
    public ResponseData<?> getProductInventory(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "15") int pageSize){
        return new ResponseData<>(HttpStatus.OK.value(), "Get product inventory successfully", productImportService.getProductInventory(pageNo, pageSize));
    }

    @PostMapping("/")
    public ResponseData<?> saveProductImport(@RequestBody ProductImportRequestDTO productImportRequestDTO){
        productImportService.createProductImport(productImportRequestDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "Save product import successfully");

    }

}
