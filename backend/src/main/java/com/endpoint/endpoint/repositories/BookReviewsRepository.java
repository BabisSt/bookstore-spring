package com.endpoint.endpoint.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endpoint.endpoint.model.BookReviews;

import com.endpoint.endpoint.model.User;

import io.micrometer.common.lang.NonNull;

@Repository
public interface BookReviewsRepository extends JpaRepository<BookReviews, Integer> {
    Optional<List<BookReviews>> findByUser(@NonNull User user);
}
