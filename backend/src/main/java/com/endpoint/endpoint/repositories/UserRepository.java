package com.endpoint.endpoint.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endpoint.endpoint.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByFirstName(String firstName);

    public Optional<User> findByLastName(String lastName);

    public Optional<User> findByEmail(String email);

    // Eventually
    // public User findByOrder(Order order);
}