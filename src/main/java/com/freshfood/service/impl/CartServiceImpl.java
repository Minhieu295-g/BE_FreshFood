package com.freshfood.service.impl;

import com.freshfood.dto.response.*;
import com.freshfood.model.Cart;
import com.freshfood.model.CartItem;
import com.freshfood.model.Product;
import com.freshfood.model.ProductVariant;
import com.freshfood.repository.CartRepository;
import com.freshfood.service.CartService;
import com.freshfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductServiceImpl productService;

    @Override
    public Cart getCartById(int id) {
        return findById(id);
    }

    @Override
    public int getTotalQuantityByCartId(int id) {
        return cartRepository.getTotalQuantityByCartId(id).orElse(0);
    }

    @Override
    public CartResponseDTO getCartResponseById(int id) {
        Cart cart = findById(id);
        Set<CartItem> cartItems = cart.getCartItems();
        Set<CartItemReponseDTO> cartItemReponseDTOS = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            cartItemReponseDTOS.add(CartItemReponseDTO.builder()
                    .id(cartItem.getId())
                    .productVariant(convertToProductVariantResponse(cartItem.getProductVariant()))
                    .quantity(cartItem.getQuantity())
                    .build());
        }
        return CartResponseDTO.builder()
                .id(cart.getId())
                .cartItem(cartItemReponseDTOS)
                .build();
    }

    private Cart findById(int id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    private ProductVariantResponseDTO convertToProductVariantResponse(ProductVariant productVariant){
        return ProductVariantResponseDTO.builder()
                .id(productVariant.getId())
                .name(productVariant.getName())
                .unit(productVariant.getUnit().toString())
                .price(productVariant.getPrice())
                .status(productVariant.getStatus().toString())
                .discountPercentage(productVariant.getDiscountPercentage())
                .thumbnailUrl(productVariant.getThumbnailUrl())
                .expiryDate(productVariant.getExpiryDate())
                .build();
    }



}
