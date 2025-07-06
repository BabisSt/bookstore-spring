package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.BookReviews;
import com.endpoint.endpoint.model.User;
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.repositories.BookRepository;
import com.endpoint.endpoint.repositories.BookReviewsRepository;
import com.endpoint.endpoint.repositories.UserRepository;

@Service
public class BookReviewsService {

    private final BookReviewsRepository bookReviewsRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BookReviewsService(BookReviewsRepository bookReviewsRepository, UserRepository userRepository,
            BookRepository bookRepository) {
        this.bookReviewsRepository = bookReviewsRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
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

    public BookReviews createBookReview(BookReviews bookReview) {
        User user = userRepository.findById(bookReview.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookReview.getBook().getIsdn())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Optional<BookReviews> existingReview = bookReviewsRepository.findByUserAndBook(user, book);
        if (existingReview.isPresent()) {
            throw new RuntimeException("This user has already reviewed this book.");
        }

        bookReview.setUser(user);
        bookReview.setBook(book);

        return bookReviewsRepository.save(bookReview);
    }

    public BookReviews updateStarsBookReview(Integer id, Integer stars, BookReviews bookReview) {
        if (bookReviewsRepository.existsById(id)) {

            bookReview.setStars(stars);
            return bookReviewsRepository.save(bookReview);
        }
        return null;
    }
}
