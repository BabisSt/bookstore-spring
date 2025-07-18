package com.endpoint.endpoint.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.endpoint.endpoint.model.Order;

import io.micrometer.common.lang.NonNull;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(@NonNull Integer userId);

    void deleteByUserId(@NonNull Integer userId);

}