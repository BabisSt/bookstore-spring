package com.endpoint.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookReviewDTO {
    private Integer stars;

    private String comment;

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("book")
    private BookDTO bookDTO;

    public BookReviewDTO() {
    }

    public BookReviewDTO(Integer stars,String comment, UserDTO userDTO, BookDTO bookDTO) {
        this.stars = stars;
        this.comment = comment;
        this.userDTO = userDTO;
        this.bookDTO = bookDTO;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public BookDTO getBookDTO() {
        return bookDTO;
    }

    public void setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }
}
