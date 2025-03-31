package com.freshfood.repository;

import com.freshfood.model.Order;
import com.freshfood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<List<Order>> findByUser(User user);
}
