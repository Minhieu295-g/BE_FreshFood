package com.freshfood.dto.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickRequestDTO implements Serializable {
    private int userId;
    private int productId;
}
