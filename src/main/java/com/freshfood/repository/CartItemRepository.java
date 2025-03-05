package com.freshfood.repository;

import com.freshfood.model.Cart;
import com.freshfood.model.CartItem;
import com.freshfood.model.Product;
import com.freshfood.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProductVariant(Cart cart, ProductVariant productVariant);}
