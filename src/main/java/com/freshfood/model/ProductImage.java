package com.freshfood.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_images")
public class ProductImage extends AbsEntity<Integer>{

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "alt_text")
    private String altText;

    @JoinColumn(name = "product_id")
    @ManyToOne
    @JsonBackReference
    private Product product;
}
