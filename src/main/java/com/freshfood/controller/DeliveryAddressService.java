package com.freshfood.controller;

import com.freshfood.dto.response.ProvinceResponseDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.api.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/delivery-address")
@RestController
@RequiredArgsConstructor
public class DeliveryAddressService {
    private final ProvinceService provinceService;
    @GetMapping("/list-province")
    public ResponseData<?> getAllProvinces() {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list province successfully", provinceService.getProvinces());
    }
}
