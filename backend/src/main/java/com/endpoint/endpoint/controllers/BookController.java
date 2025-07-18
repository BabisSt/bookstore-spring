/*
 * REST controller for handling HTTP requests related to Book entities.
 * 
 * Maps requests under "/api/books" and delegates operations to the BookService.
 * Provides endpoints to:
 * - Retrieve all books or filter by author, title, or ISDN.
 * - Create new books via POST.
 * - Update existing books via PUT.
 * - Delete books via DELETE.
 * 
 * Uses Spring's @RestController for RESTful web services and
 * @Autowired to inject the BookService dependency.
 * 
 * Note: Some endpoint mappings use path variables that may conflict (e.g., author, title, isdn),
 * consider differentiating the URL patterns to avoid ambiguity.
 */

package com.endpoint.endpoint.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.endpoint.endpoint.dto.BookDTO;
import com.endpoint.endpoint.enums.BookGenre;
import com.endpoint.endpoint.model.Author;
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/author/{author}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<List<BookDTO>> getBooksByAuthor(@PathVariable Author author) {
        return bookService.getBookByAuthor(author);
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<BookDTO> getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Book> searchBooks(
            @RequestParam(required = false) String isdn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorFirstName,
            @RequestParam(required = false) String authorLastName,
            @RequestParam(required = false) BookGenre bookGenre) {
        return bookService.searchBooks(isdn, title, authorFirstName, authorLastName, bookGenre);
    }

    @GetMapping("/isdn/{isdn}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<BookDTO> getBookByIsdn(@PathVariable String isdn) {
        return bookService.getBookByIsdn(isdn);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createBook(@RequestBody @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Book createdBook = bookService.createBook(book);
        return ResponseEntity.ok(createdBook);
    }

    
    @PutMapping("/updateStock/{isdn}/{newStock}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO updateBookStock(@PathVariable("isdn") String isdn, @PathVariable("newStock") Integer newStock) {
        return bookService.updateBookStock(isdn, newStock);
    }


    @PutMapping("/updateStock/{isdn}/{newTitle}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO updateBookTitle(@PathVariable("isdn") String isdn, @PathVariable("newTitle") String newTitle) {
        return bookService.updateBookTitle(isdn, newTitle);
    }

    @PutMapping("updateAuthor/{isdn}/{newAuthorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO updateBookAuthor(@PathVariable("isdn") String isdn,
            @PathVariable("newAuthorId") Integer newAuthorId) {
        return bookService.updateBookAuthor(isdn, newAuthorId);
    }

    @PutMapping("/addBookGenre/{isdn}/{newBookGenre}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO addBookGenre(@PathVariable("isdn") String isdn, @PathVariable("newBookGenre") BookGenre newBookGenre,
            @RequestBody @Valid Book book) {
        return bookService.addBookGenre(isdn, newBookGenre, book);
    }

    @PutMapping("/removeBookGenre/{isdn}/{removedBookGenre}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO removeBookGenre(@PathVariable("isdn") String isdn,
            @PathVariable("removedBookGenre") BookGenre removedBookGenre,
            @RequestBody @Valid Book book) {
        return bookService.removeBookGenre(isdn, removedBookGenre, book);
    }

    @DeleteMapping("/deleteBook/{isdn}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteBook(@PathVariable String isdn) {
        return bookService.deleteBook(isdn);
    }
}