package com.freshfood.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_imports")
public class ProductImport extends AbsEntity<Integer>{
    @JoinColumn(name = "product_variant_id")
    @ManyToOne
    private ProductVariant productVariant;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "quantity")
    private int quantity;
}
