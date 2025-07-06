package com.endpoint.endpoint.dto;

public class BookReviewDTO {
    private Integer stars;
    private UserDTO userDTO;
    private BookDTO bookDTO;

    public BookReviewDTO() {
    }

    public BookReviewDTO(Integer stars, UserDTO userDTO, BookDTO bookDTO) {
        this.stars = stars;
        this.userDTO = userDTO;
        this.bookDTO = bookDTO;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
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
