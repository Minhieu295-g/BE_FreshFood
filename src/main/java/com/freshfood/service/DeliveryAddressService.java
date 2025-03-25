package com.freshfood.service;

import com.freshfood.dto.request.DeliveryAddressRequestDTO;
import com.freshfood.dto.response.DeliveryAddressResponseDTO;
import com.freshfood.dto.response.DeliveryFeeResponseDTO;
import com.freshfood.model.DeliveryAddress;
import com.freshfood.model.User;

public interface DeliveryAddressService {

    int addDeliveryAddress(DeliveryAddressRequestDTO deliveryAddressRequestDTO);
    void updateDeliveryAddress(int id, DeliveryAddressRequestDTO deliveryAddressRequestDTO);
    void deleteDeliveryAddress(int id);
    DeliveryAddress getDeliveryAddressById(int id);
    DeliveryAddressResponseDTO getDeliveryAddressDefault(int userId, boolean isDefault);
    DeliveryFeeResponseDTO getDeliveryFeeResponse(int deliveryAddressId);
}
