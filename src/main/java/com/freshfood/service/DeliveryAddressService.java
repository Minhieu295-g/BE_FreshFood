package com.freshfood.service;

import com.freshfood.dto.request.DeliveryAddressRequestDTO;

public interface DeliveryAddressService {

    int addDeliveryAddress(DeliveryAddressRequestDTO deliveryAddressRequestDTO);
    void updateDeliveryAddress(int id, DeliveryAddressRequestDTO deliveryAddressRequestDTO);
    void deleteDeliveryAddress(int id);

}
