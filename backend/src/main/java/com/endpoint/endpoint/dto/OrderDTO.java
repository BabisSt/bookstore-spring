package com.endpoint.endpoint.dto;

import java.util.List;

import com.endpoint.endpoint.model.BookAmountPair;

public class OrderDTO {
    private Integer userId;
    private List<BookAmountPair> books;

    public OrderDTO() {
    }

    public OrderDTO(Integer userId, List<BookAmountPair> books) {
        this.userId = userId;
        this.books = books;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUser(Integer userId) {
        this.userId = userId;
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
                ", user=" + userId +
                ", books=" + books +
                '}';
    }
}
