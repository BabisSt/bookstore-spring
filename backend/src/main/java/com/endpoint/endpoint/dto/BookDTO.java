/**
 * BookDTO (Data Transfer Object) is used to expose a simplified version of the Book entity
 * through the API. It helps decouple the internal data model from the external representation,
 * improving security and flexibility.
 *
 * This DTO only includes selected fields to be sent to or received from clients,
 * such as during create/update operations or API responses.
 */

package com.endpoint.endpoint.dto;

import java.util.List;

import com.endpoint.endpoint.enums.BookGenre;
import com.endpoint.endpoint.model.Author;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookDTO {

    @Id
    // @NotBlank
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISDN must be either 10 or 13 digits")
    private String isdn;

    @NotBlank(message = "Title can't be empty")
    private String title;

    // @NotBlank
    private Author author;

    @Enumerated(EnumType.STRING)
    private List<BookGenre> bookGenre;
    // Constructors
    public BookDTO() {
    }

    public BookDTO(String isdn, String title, Author author, List<BookGenre> bookGenre) {
        this.isdn = isdn;
        this.title = title;
        this.author = author;
        this.bookGenre = bookGenre;
    }

    // Getters and setters
    public String getIsdn() {
        return this.isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<BookGenre> getBookGenre() {
        return this.bookGenre;
    }

    public void setBookGenre(List<BookGenre> bookGenre) {
        this.bookGenre = bookGenre;
    }
}
