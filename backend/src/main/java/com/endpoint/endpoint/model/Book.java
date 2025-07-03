package com.endpoint.endpoint.model;

import java.util.Date;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

import com.endpoint.endpoint.enums.BookGenre;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "books")
public class Book {

    @Id
    // @NotBlank
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISDN must be either 10 or 13 digits")
    @Column(name = "isdn")
    private String isdn;

    @NotBlank(message = "Title can't be empty")
    @Column(name = "title")
    private String title;

    // @NotBlank this is used only on strings
    @ManyToOne // Each Book Has One Author
    @JoinColumn(name = "author_id")
    private Author author;

    // @NotBlank
    @Column(name = "release_date")
    private Date releaseDate;

    @NotBlank(message = "Content can't be empty")
    @Column(name = "content")
    private String content;

    //connecting the books with there genres , they are enums. Can't user ManyToMany because genres are enums and not an entity
    @ElementCollection(targetClass = BookGenre.class)
    @CollectionTable(
        name = "book_genres",
        joinColumns = @JoinColumn(name = "book_isdn")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "book_genre")
    private List<BookGenre> bookGenre;

    public Book() {
    };

    public Book(String isdn, String title, Author author, Date releaseDate, String content, List<BookGenre> bookGenre) {
        this.isdn = isdn;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.content = content;
        this.bookGenre = bookGenre;
    }

    public String getIsdn() {
        return this.isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<BookGenre> getBookGenre() {
        return this.bookGenre;
    }

    public void setBookGenre(List<BookGenre> bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void addBookGenre(BookGenre bookGenre) {
        this.bookGenre.add(bookGenre);
    }

    public void removeBookGenre(BookGenre bookGenre) {
        this.bookGenre.remove(bookGenre);
    }
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("isdn", this.getIsdn())
                .append("title", this.getTitle())
                .append("author", this.getAuthor())
                .append("releadeDate", this.getReleaseDate())
                .append("content", this.getContent())
                .append("bookGenre", this.getBookGenre()).toString();
    }
}
