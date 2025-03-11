package com.freshfood.service.impl;

import com.freshfood.dto.request.CartItemRequestDTO;
import com.freshfood.model.Cart;
import com.freshfood.model.CartItem;
import com.freshfood.model.Product;
import com.freshfood.model.ProductVariant;
import com.freshfood.repository.CartItemRepository;
import com.freshfood.service.CartItemService;
import com.freshfood.service.CartService;
import com.freshfood.service.ProductService;
import com.freshfood.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartService cartService;
    private final ProductVariantService productVariantService;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem getCartItem(int id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public int saveCartItem(CartItemRequestDTO cartItemRequestDTO) {
        Cart cart = cartService.getCartById(cartItemRequestDTO.getCartId());
        ProductVariant productVariant = productVariantService.getProductVariantById(cartItemRequestDTO.getProductVariantId());
        Optional<CartItem> cartItemAvailable = cartItemRepository.findByCartAndProductVariant(cart, productVariant);

        // Kiểm tra xem CartItem đã tồn tại hay chưa
        if (cartItemAvailable.isPresent()) {
            // Nếu CartItem đã tồn tại, cập nhật số lượng
            CartItem cartItem = cartItemAvailable.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequestDTO.getQuantity());
            cartItemRepository.save(cartItem);
            return cartItem.getId(); // Trả về ID của CartItem đã cập nhật
        } else {
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .productVariant(productVariant)
                    .quantity(cartItemRequestDTO.getQuantity())
                    .build();
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Override
    public void updateCartItem(int id, CartItemRequestDTO cartItemRequestDTO) {
        CartItem cartItem = getCartItem(id);
        cartItem.setQuantity(cartItemRequestDTO.getQuantity());
        if(cartItem.getQuantity() <= 0){
            cartItemRepository.delete(cartItem);
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
    }



}
