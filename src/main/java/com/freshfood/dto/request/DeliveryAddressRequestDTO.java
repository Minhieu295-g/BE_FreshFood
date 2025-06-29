package com.freshfood.dto.request;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddressRequestDTO implements Serializable {
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
