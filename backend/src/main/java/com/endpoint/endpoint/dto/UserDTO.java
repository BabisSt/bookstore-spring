package com.endpoint.endpoint.dto;

import java.util.List;

import com.endpoint.endpoint.model.BookReviews;
import com.endpoint.endpoint.model.Person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO extends Person {

    @NotBlank
    @Email
    private String email;

    private List<BookReviews> bookReviews;

    public UserDTO() {
    };

    public UserDTO(String firstName, String lastName, String email, String aboutSection,
            List<BookReviews> bookReviews) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.email = email;
        super.setAboutSection(aboutSection);
        this.bookReviews = bookReviews;

    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BookReviews> getBookReviews() {
        return this.bookReviews;
    }

    public void setBookReviews(List<BookReviews> bookReviews) {
        this.bookReviews = bookReviews;
    }

}
