package com.freshfood.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class DeliveryFeeResponseDTO {
    private String deliveryFee;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String deliveryDate;
}
