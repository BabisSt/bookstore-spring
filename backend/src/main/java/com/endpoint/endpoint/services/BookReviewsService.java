package com.endpoint.endpoint.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.BookReviews;
import com.endpoint.endpoint.model.User;
import com.endpoint.endpoint.dto.BookReviewDTO;
import com.endpoint.endpoint.mapper.BookReviewMapper;
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.repositories.BookRepository;
import com.endpoint.endpoint.repositories.BookReviewsRepository;
import com.endpoint.endpoint.repositories.UserRepository;

import jakarta.transaction.Transactional;

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

    public List<BookReviewDTO> getAllBookReviews() {
        List<BookReviews> reviews = bookReviewsRepository.findAll();
        List<BookReviewDTO> reviewsDTO = reviews.stream()
                .map(b -> BookReviewMapper.toDTO(b))
                .collect(Collectors.toList());
        return reviewsDTO;
    }

    public List<BookReviewDTO> getBookReviewsByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<BookReviews> reviews = bookReviewsRepository.findByUser(user);
        List<BookReviewDTO> reviewsDTO = reviews.stream()
                .map(b -> BookReviewMapper.toDTO(b))
                .collect(Collectors.toList());
        return reviewsDTO;
    }

    public BookReviewDTO getBookReviewsById(Integer id) {
        BookReviews review = bookReviewsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return BookReviewMapper.toDTO(review);
    }

    public BookReviewDTO createBookReview(BookReviewDTO bookReviewDTO) {
        User user = userRepository.findById(bookReviewDTO.getUserDTO().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookReviewDTO.getBookDTO().getIsdn())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookReviews existingReview = bookReviewsRepository.findByUserAndBook(user, book);
        if (existingReview != null) {
            throw new RuntimeException("This user has already reviewed this book.");
        }

        BookReviews bookReviewEntity = BookReviewMapper.toEntity(bookReviewDTO);
        bookReviewEntity.setUser(user); // persistent User from DB
        bookReviewEntity.setBook(book); // persistent Book from DB

        bookReviewsRepository.save(bookReviewEntity);

        return bookReviewDTO;
    }

    public BookReviewDTO updateStarsBookReview(Integer id, Integer stars) {
        if (bookReviewsRepository.existsById(id)) {
            BookReviews bookReview = bookReviewsRepository.findById(id).get();
            bookReview.setStars(stars);
            bookReviewsRepository.save(bookReview);

            return BookReviewMapper.toDTO(bookReview);
        }
        return null;
    }

    @Transactional
    public boolean deleteBookReview(Integer id) {
        if (bookReviewsRepository.existsById(id)) {
            bookReviewsRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
