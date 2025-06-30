package com.endpoint.endpoint.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.endpoint.endpoint.model.Order;
import com.endpoint.endpoint.model.User;

import io.micrometer.common.lang.NonNull;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<List<Order>> findByUser(@NonNull User user);

    void deleteByUserId(@NonNull Integer userId);

}