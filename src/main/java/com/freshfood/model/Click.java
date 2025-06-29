package com.freshfood.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clicks")
public class Click extends AbsEntity<Integer>{
    @Column(name = "user_id")
    private int userId;
    @Column(name = "product_id")
    private int productId;
}
