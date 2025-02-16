package com.freshfood.dto.response;

import com.freshfood.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
public class ParentCategoryResponseDTO implements Serializable {
    private int id;
    private String name;
    private String imageUrl;
    private Set<Category> categories;
}
