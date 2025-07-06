package com.endpoint.endpoint.dto;

import java.util.List;

import com.endpoint.endpoint.model.BookAmountPair;

public class OrderDTO {
    private UserDTO user;
    private List<BookAmountPair> books;

    public OrderDTO() {
    }

    public OrderDTO(UserDTO user, List<BookAmountPair> books) {
        this.user = user;
        this.books = books;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<BookAmountPair> getBooks() {
        return books;
    }

    public void setBooks(List<BookAmountPair> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                ", user=" + user +
                ", books=" + books +
                '}';
    }
}
