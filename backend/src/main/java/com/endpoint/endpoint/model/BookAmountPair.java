package com.endpoint.endpoint.model;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_books")
public class BookAmountPair extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference // used to prevent infinite recursion when serializing parent-child
                       // relationships.
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_isdn")
    @JsonIgnoreProperties(value = { "author", "bookReviews" })
    private Book book;

    @Column(name = "amount")
    private Integer amount;

    public BookAmountPair() {
    };

    public BookAmountPair(Order order, Book book, Integer amount) {
        this.order = order;
        this.book = book;
        this.amount = amount;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", super.getId())
                .append("order", this.order.toString())
                .append("book", this.book.toString())
                .append("amount", this.amount.toString()).toString();
    }
}