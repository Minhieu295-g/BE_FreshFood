package com.freshfood.dto.request;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryAddressRequestDTO {
    private String name;

    private String numberPhone;

    private int provinceId;

    private int districtId;

    private int wardId;

    private String provinceName;

    private String districtName;

    private String wardName;

    private String detailAddress;

    private int userId;
}
