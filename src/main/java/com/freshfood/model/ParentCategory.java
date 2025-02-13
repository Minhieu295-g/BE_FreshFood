package com.freshfood.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parent_categories")
public class ParentCategory extends AbsEntity<Integer> {
    @Column(name = "name")
    private String name;

    @Column(name="image_url")
    private String imageUrl;

//    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private Set<Category> categories = new HashSet<>();
}
