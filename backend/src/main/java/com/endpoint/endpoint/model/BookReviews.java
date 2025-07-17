package com.endpoint.endpoint.model;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book_reviews")
public class BookReviews extends BaseEntity {

    @NotNull(message = "Stars must not be null")
    @Min(value = 1, message = "Stars must be at least 1")
    @Max(value = 5, message = "Stars must be at most 5")
    @Column(name = "stars")
    private Integer stars;

    @Size(min = 1, max = 500)
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({ "bookReviews" }) // prevents recursion
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_isdn")
    @JsonIgnoreProperties({ "bookReviews" })
    private Book book;

    public BookReviews() {
    };

    public BookReviews(Integer stars,String comment, User user, Book book) {
        this.stars = stars;
        this.comment = comment;
        this.user = user;
        this.book = book;
    }

    public Integer getStars() {
        return this.stars;
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("book", this.getBook())
                .append("user", this.getUser())
                .append("stars", this.getStars())
                .append("comment", this.getComment()).toString();
    }

}
