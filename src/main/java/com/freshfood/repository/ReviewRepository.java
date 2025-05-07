package com.freshfood.repository;

import com.freshfood.model.Product;
import com.freshfood.model.Review;
import com.freshfood.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findByProduct(Product product, Pageable pageable);
}
