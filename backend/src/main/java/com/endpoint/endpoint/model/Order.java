package com.endpoint.endpoint.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // used to prevent infinite recursion when serializing parent-child
                          // relationships.
    private List<BookAmountPair> books = new ArrayList<>();

    public Order() {
    };

    public Order(Integer userId, List<BookAmountPair> books) {
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
        return this.books;
    }

    public void setBooks(List<BookAmountPair> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", super.getId())
                .append("user", this.userId.toString())
                .append("book", this.books.toString()).toString();
    }

}
