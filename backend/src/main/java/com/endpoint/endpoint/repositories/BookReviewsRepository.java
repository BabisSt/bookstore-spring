package com.endpoint.endpoint.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.model.BookReviews;

import com.endpoint.endpoint.model.User;

import io.micrometer.common.lang.NonNull;

@Repository
public interface BookReviewsRepository extends JpaRepository<BookReviews, Integer> {
    List<BookReviews> findByUser(@NonNull User user);

    BookReviews findByUserAndBook(User user, Book book);

}
