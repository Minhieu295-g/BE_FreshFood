package com.freshfood.repository;

import com.freshfood.model.DeliveryAddress;
import com.freshfood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {
    Optional<List<DeliveryAddress>> findByUser(User user);
    Optional<DeliveryAddress> findByUserAndIsDefault(User user, boolean isDefault);}
