package com.endpoint.endpoint.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endpoint.endpoint.model.User;

import io.micrometer.common.lang.NonNull;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByFirstName(@NonNull String firstName);

    public Optional<User> findByLastName(@NonNull String lastName);

    public Optional<User> findByEmail(@NonNull String email);
}