package com.freshfood.service.impl;

import com.freshfood.dto.request.DeliveryAddressRequestDTO;
import com.freshfood.dto.response.DeliveryAddressResponseDTO;
import com.freshfood.model.DeliveryAddress;
import com.freshfood.model.User;
import com.freshfood.repository.DeliveryAddressRepository;
import com.freshfood.service.DeliveryAddressService;
import com.freshfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final UserService userService;
    @Override
    public int addDeliveryAddress(DeliveryAddressRequestDTO deliveryAddressRequestDTO) {
        User user = userService.findByUserId(deliveryAddressRequestDTO.getUserId());
        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .name(deliveryAddressRequestDTO.getName())
                .numberPhone(deliveryAddressRequestDTO.getNumberPhone())
                .provinceId(deliveryAddressRequestDTO.getProvinceId())
                .districtId(deliveryAddressRequestDTO.getDistrictId())
                .wardId(deliveryAddressRequestDTO.getWardId())
                .provinceName(deliveryAddressRequestDTO.getProvinceName())
                .districtName(deliveryAddressRequestDTO.getDistrictName())
                .wardName(deliveryAddressRequestDTO.getWardName())
                .detailAddress(deliveryAddressRequestDTO.getDetailAddress())
                .isDefault(true)
                .user(user)
                .build();
        Optional<List<DeliveryAddress>> address = deliveryAddressRepository.findByUser(user);
        if(address.isPresent()) {
            deliveryAddress.setDefault(false);
        }
        deliveryAddressRepository.save(deliveryAddress);
        return deliveryAddress.getId();
    }

    @Override
    public void updateDeliveryAddress(int id, DeliveryAddressRequestDTO deliveryAddressRequestDTO) {

    }

    @Override
    public void deleteDeliveryAddress(int id) {

    }

    @Override
    public DeliveryAddress getDeliveryAddressById(int id) {
        return deliveryAddressRepository.findById(id).orElse(null);
    }

    @Override
    public DeliveryAddressResponseDTO getDeliveryAddressDefault(int userId, boolean isDefault) {
        User user = userService.findByUserId(userId);
        Optional<DeliveryAddress> deliveryAddress = deliveryAddressRepository.findByUserAndIsDefault(user, isDefault);
        if(deliveryAddress.isPresent()){
            DeliveryAddress address = deliveryAddress.get();
            return DeliveryAddressResponseDTO.builder()
                    .id(address.getId())
                    .provinceId(address.getProvinceId())
                    .wardId(address.getWardId())
                    .districtId(address.getDistrictId())
                    .provinceName(address.getProvinceName())
                    .wardName(address.getWardName())
                    .districtName(address.getDistrictName())
                    .detailAddress(address.getDetailAddress())
                    .isDefault(address.isDefault())
                    .name(address.getName())
                    .numberPhone(address.getNumberPhone())
                    .build();
        }
        return null;
    }
}
