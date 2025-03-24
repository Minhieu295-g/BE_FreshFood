package com.freshfood.controller;

import com.freshfood.dto.request.DeliveryAddressRequestDTO;
import com.freshfood.dto.response.DeliveryAddressResponseDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.DeliveryAddressService;
import com.freshfood.service.api.GiaoHangNhanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/delivery-address")
@RestController
@RequiredArgsConstructor
public class DeliveryAddressController {
    private final GiaoHangNhanhService giaoHangNhanhService;
    private final DeliveryAddressService deliveryAddressService;
    @GetMapping("/list-provinces")
    public ResponseData<?> getAllProvinces() {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list provinces successfully", giaoHangNhanhService.getProvinces());
    }

    @GetMapping("/list-districts")
    public ResponseData<?> getDistricts(@RequestParam int provincetId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list districts successfully", giaoHangNhanhService.getDistricts(provincetId));
    }

    @GetMapping("/list-wards")
    public ResponseData<?> getWards(@RequestParam int districtId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list districts successfully", giaoHangNhanhService.getWards(districtId));
    }

    @PostMapping("/")
    public ResponseData<?> addDeliveryAddress(@RequestBody DeliveryAddressRequestDTO deliveryAddress) {
        return new ResponseData<>(HttpStatus.OK.value(), "Save Delivery Address successfully!", deliveryAddressService.addDeliveryAddress(deliveryAddress));
    }

    @GetMapping("/default")
    public ResponseData<?> getDeliveryAddressDefault(@RequestParam int userId, @RequestParam boolean isDefault) {
        DeliveryAddressResponseDTO result = deliveryAddressService.getDeliveryAddressDefault(userId, isDefault);
        return new ResponseData<>(HttpStatus.OK.value(), "Get default address successfully!", result);
    }

}
