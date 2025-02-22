package com.freshfood.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.freshfood.util.ProductStatus;
import com.freshfood.util.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends AbsEntity<Integer>{
    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ProductVariant> productVariants = new HashSet<>();

}
