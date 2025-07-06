package com.endpoint.endpoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endpoint.endpoint.model.User;

import io.micrometer.common.lang.NonNull;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByFirstName(@NonNull String firstName);

    public User findByLastName(@NonNull String lastName);

    public User findByEmail(@NonNull String email);
}