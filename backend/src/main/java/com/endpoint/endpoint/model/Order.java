package com.endpoint.endpoint.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @NotNull(message = "User must not be null")
    @ManyToOne // Each Order Has One User
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // used to prevent infinite recursion when serializing parent-child
                          // relationships.
    private List<BookAmountPair> books = new ArrayList<>();

    public Order() {
    };

    public Order(User user, List<BookAmountPair> books) {
        this.user = user;
        this.books = books;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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
                .append("user", this.user.toString())
                .append("book", this.books.toString()).toString();
    }

}
