package com.freshfood.repository;

import com.freshfood.model.DeliveryAddress;
import com.freshfood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {
    Optional<DeliveryAddress> findByUser(User user);
}
