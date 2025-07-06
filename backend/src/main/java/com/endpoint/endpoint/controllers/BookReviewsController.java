package com.endpoint.endpoint.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.endpoint.endpoint.model.BookReviews;

import com.endpoint.endpoint.services.BookReviewsService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

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

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createBookReview(@RequestBody @Valid BookReviews bookReview, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        BookReviews newReview = bookReviewsService.createBookReview(bookReview);
        return ResponseEntity.ok(newReview);
    }

    @PutMapping("/updateStars/{id}/{stars}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookReviews updateStarsBookReview(@PathVariable Integer id, @PathVariable Integer stars,
            @RequestBody @Valid BookReviews bookReview) {
        return bookReviewsService.updateStarsBookReview(id, stars, bookReview);
    }
}
