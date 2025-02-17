package com.freshfood.service.impl;

import com.freshfood.dto.request.ProductRequestDTO;
import com.freshfood.model.Product;
import com.freshfood.model.ProductImage;
import com.freshfood.repository.CategoryRepository;
import com.freshfood.repository.ProductRepository;
import com.freshfood.service.CategoryService;
import com.freshfood.service.CloudinaryService;
import com.freshfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    @Override
    public int addProduct(ProductRequestDTO productRequestDTO, String thumbnailUrl, String[] imageUrl) {
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .category(categoryService.getCategoryById(productRequestDTO.getCategoryId()))
                .thumbnailUrl(thumbnailUrl)
                .build();
        product.setProductImages(convertToProductImage(imageUrl, product));
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public void updateProduct(int id, ProductRequestDTO productRequestDTO, String thumbnailUrl, String[] imageUrl) {

    }

    @Override
    public void deleteProduct(int id) {

    }

    @Override
    public Product getProduct(int id) {
        return null;
    }

    private HashSet<ProductImage> convertToProductImage(String[] urlImage, Product product) {
        HashSet<ProductImage> productImages = new HashSet<>();
        for (int i = 0; i < urlImage.length; i++) {
            productImages.add(ProductImage.builder()
                    .imageUrl(urlImage[i])
                    .altText("products")
                    .product(product)
                    .build());
        }
        return productImages;
    }
}
