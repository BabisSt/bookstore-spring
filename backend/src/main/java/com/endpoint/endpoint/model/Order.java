package com.endpoint.endpoint.model;

import java.util.List;

import org.springframework.core.style.ToStringCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @NotNull(message = "Amount must not be null")
    private Integer amount;

    @NotNull(message = "User must not be null")
    @ManyToOne // Each Order Has One User
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotEmpty(message = "Order must contain at least one book")
    @ManyToMany
    @JoinTable(name = "order_books", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "book_isdn"))
    private List<Book> books; // note plural

    public Order() {
    };

    public Order(Integer amount, User user, List<Book> books) {
        this.amount = amount;
        this.user = user;
        this.books = books;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
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
