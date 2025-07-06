package com.endpoint.endpoint.mapper;

import com.endpoint.endpoint.dto.BookReviewDTO;
import com.endpoint.endpoint.model.BookReviews;

public class BookReviewMapper {

    public static BookReviewDTO toDTO(BookReviews review) {
        if (review == null) {
            return null;
        }

        return new BookReviewDTO(
                review.getStars(),
                UserMapper.toDTO(review.getUser()),
                BookMapper.toDTO(review.getBook()));
    }

    public static BookReviews toEntity(BookReviewDTO bookReviewDTO) {
        if (bookReviewDTO == null) {
            return null;
        }

        return new BookReviews(
                bookReviewDTO.getStars(),
                UserMapper.toEntity(bookReviewDTO.getUserDTO()),
                BookMapper.toEntity(bookReviewDTO.getBookDTO()));
    }

}
