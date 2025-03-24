package com.freshfood.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryAddressResponseDTO {

    private int id;

    private String name;

    private String numberPhone;

    private int provinceId;

    private int districtId;

    private int wardId;

    private String provinceName;

    private String districtName;

    private String wardName;

    private String detailAddress;

    private boolean isDefault;

}
