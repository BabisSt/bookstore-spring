package com.endpoint.endpoint.model;

import java.util.List;

import org.springframework.core.style.ToStringCreator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User extends Person {

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "Password can't be empty")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReviews> bookReviews;

    public User() {
    };

    public User(String firstName, String lastName, String email, String password, String aboutSection,
            List<BookReviews> bookReviews) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.email = email;
        this.password = password;
        super.setAboutSection(aboutSection);
        this.bookReviews = bookReviews;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BookReviews> getBookReviews() {
        return this.bookReviews;
    }

    public void setBookReviews(List<BookReviews> bookReviews) {
        this.bookReviews = bookReviews;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", super.getId())
                .append("firstName", super.getFirstName())
                .append("lastName", super.getLastName())
                .append("email", this.getEmail())
                .append("password", this.getPassword())
                .append("bookReviews", this.getBookReviews()).toString();
    }
}
