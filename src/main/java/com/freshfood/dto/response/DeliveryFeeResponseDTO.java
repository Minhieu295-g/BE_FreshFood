package com.freshfood.dto.response;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class DeliveryFeeResponseDTO {
    private String deliveryFee;
    private String deliveryDate;
}
