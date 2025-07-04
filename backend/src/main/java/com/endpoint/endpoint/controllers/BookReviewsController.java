package com.endpoint.endpoint.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.endpoint.endpoint.model.BookReviews;

import com.endpoint.endpoint.services.BookReviewsService;

@RestController
@RequestMapping("api/bookReviews")
public class BookReviewsController {
    @Autowired
    private BookReviewsService bookReviewsService;

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<BookReviews> getAllBookReviews() {
        return bookReviewsService.getAllBookReviews();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<BookReviews> getBookReviewsById(@PathVariable Integer id) {
        return bookReviewsService.getBookReviewsById(id);
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<List<BookReviews>> getBookReviewsByUser(@PathVariable Integer user_id) {
        return bookReviewsService.getBookReviewsByUser(user_id);
    }
}
