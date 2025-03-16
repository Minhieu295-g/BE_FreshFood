package com.freshfood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DistrictResponseDTO {
    private int districtId;
    private String districtName;
}
