package com.endpoint.endpoint.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.core.style.ToStringCreator;

import com.endpoint.endpoint.enums.BookGenre;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    // connecting the books with there genres , they are enums. Can't user
    // ManyToMany because genres are enums and not an entity
    @ElementCollection(targetClass = BookGenre.class)
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_isdn"))
    @Enumerated(EnumType.STRING)
    @Column(name = "book_genre")
    private Set<BookGenre> bookGenre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReviews> bookReviews;

    public Book() {
    };

    public Book(String isdn, String title, Author author, Date releaseDate, String content, Set<BookGenre> bookGenre,
            List<BookReviews> bookReviews) {
        this.isdn = isdn;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.content = content;
        this.bookGenre = bookGenre;
        this.bookReviews = bookReviews;
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

    public Set<BookGenre> getBookGenre() {
        return this.bookGenre;
    }

    public void setBookGenre(Set<BookGenre> bookGenre) {
        this.bookGenre = bookGenre;
    }

    public void addBookGenre(BookGenre bookGenre) {
        this.bookGenre.add(bookGenre);
    }

    public void removeBookGenre(BookGenre bookGenre) {
        this.bookGenre.remove(bookGenre);
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
                .append("isdn", this.getIsdn())
                .append("title", this.getTitle())
                .append("author", this.getAuthor())
                .append("releadeDate", this.getReleaseDate())
                .append("content", this.getContent())
                .append("bookGenre", this.getBookGenre())
                .append("bookReviews", this.getBookReviews()).toString();
    }
}
