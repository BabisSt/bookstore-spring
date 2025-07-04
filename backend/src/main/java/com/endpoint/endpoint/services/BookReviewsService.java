package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.BookReviews;
import com.endpoint.endpoint.repositories.BookReviewsRepository;
import com.endpoint.endpoint.repositories.UserRepository;

@Service
public class BookReviewsService {

    private final BookReviewsRepository bookReviewsRepository;
    private final UserRepository userRepository;

    public BookReviewsService(BookReviewsRepository bookReviewsRepository, UserRepository userRepository) {
        this.bookReviewsRepository = bookReviewsRepository;
        this.userRepository = userRepository;
    }

    public List<BookReviews> getAllBookReviews() {
        return bookReviewsRepository.findAll();
    }

    public Optional<List<BookReviews>> getBookReviewsByUser(Integer userId) {
        return userRepository.findById(userId)
                .flatMap(user -> bookReviewsRepository.findByUser(user));
    }

    public Optional<BookReviews> getBookReviewsById(Integer id) {
        return bookReviewsRepository.findById(id);
    }

    // public Order createOrder(Order order) {
    // User user = userRepository.findById(order.getUser().getId())
    // .orElseThrow(() -> new RuntimeException("User not found"));
    // List<BookAmountPair> checkedBooks = order.getBooks().stream()
    // .map(pair -> {
    // Book freshBook = bookRepository.findById(pair.getBook().getIsdn())
    // .orElseThrow(() -> new RuntimeException("Book not found: " +
    // pair.getBook().getIsdn()));
    // pair.setBook(freshBook); // update the book inside the pair
    // return pair;
    // })
    // .collect(Collectors.toList());

    // order.setUser(user);
    // order.setBooks(checkedBooks);
    // return orderRepository.save(order);
    // }
}
