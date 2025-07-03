/*
 * Service layer for managing Book entities.
 * 
 * This class acts as an intermediary between controllers and the BookRepository,
 * providing methods to perform CRUD operations:
 * - Retrieve all books or filter by author, title, or ISDN.
 * - Create new books.
 * - Update existing books by ISDN.
 * - Delete books by ISDN.
 * 
 * Uses Spring's @Service annotation for service component detection and
 * @Autowired to inject the BookRepository dependency.
 * 
 * Note: Update returns null if the book with given ISDN does not exist.
 */

package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.dto.BookDTO;
import com.endpoint.endpoint.enums.BookGenre;
import com.endpoint.endpoint.mapper.BookMapper;
import com.endpoint.endpoint.model.Author;
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.repositories.BookRepository;
import com.endpoint.endpoint.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookService() {
    };

    /*
     * The purpose of this function is to get all the books via the findAll()
     * function from the repository then feed it through a stream , change them to
     * DTO and return them as a list. This way I don't expose the content and
     * releaseDate of the book.
     */
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(b -> BookMapper.toDTO(b))
                .collect(Collectors.toList());
    }

    public Optional<List<BookDTO>> getBookByAuthor(Author author) {
        Optional<List<Book>> listOfBooks = bookRepository.findByAuthor(author);

        return listOfBooks.map(books -> books.stream().map(b -> BookMapper.toDTO(b)).collect(Collectors.toList()));
    }

    public Optional<BookDTO> getBookByTitle(String title) {

        Optional<Book> book = bookRepository.findByTitle(title);
        return BookMapper.OptionaltoDTO(book);
    }

    public Optional<BookDTO> getBookByIsdn(String isdn) {
        Book book = bookRepository.findByIsdn(isdn);
        return Optional.ofNullable(BookMapper.toDTO(book));
    }

    public Book createBook(Book book) {

        // check if author exists
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author must be provided with a valid ID.");
        }

        // if author exists then link it with the new book
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Author not found with id: " + book.getAuthor().getId()));
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);

        return savedBook;
    }

    // Requires the whole Book entity to be sent in order to verify it
    public BookDTO updateBookTitle(String isdn, String newTitle, Book book) {
        if (bookRepository.existsById(isdn)) {
            book.setTitle(newTitle);
            Book savedBook = bookRepository.save(book);
            return BookMapper.toDTO(savedBook);
        }
        return null;
    }

    // Requires the whole Book entity to be sent in order to verify it
    public BookDTO updateBookAuthor(String isdn, Integer newAuthorId, Book book) {
        if (bookRepository.existsById(isdn)) {
            // if author exists then link it with the book
            Author author = authorRepository.findById(newAuthorId)
                    .orElseThrow(
                            () -> new EntityNotFoundException("Author not found with id: " + newAuthorId));
            book.setAuthor(author);
            Book savedBook = bookRepository.save(book);
            return BookMapper.toDTO(savedBook);
        }
        return null;
    }

    // Requires the whole Book entity to be sent in order to verify it
    public BookDTO addBookGenre(String isdn, BookGenre newBookGenre, Book book) {
        if (bookRepository.existsById(isdn)) {
            // if genre exists then link it with the book
            for (BookGenre bookGenre : BookGenre.values()){
                if(bookGenre.name().equalsIgnoreCase(newBookGenre.toString())){
                    book.addBookGenre(newBookGenre);
                    Book savedBook = bookRepository.save(book);
                    return BookMapper.toDTO(savedBook);
                }
            }
        }
        return null;
    }

    public BookDTO removeBookGenre(String isdn, BookGenre deletingBookGenre, Book book) {
        if (bookRepository.existsById(isdn)) {
            for (BookGenre bookGenre : BookGenre.values()){
                if(bookGenre.name().equalsIgnoreCase(deletingBookGenre.toString())){
                    book.removeBookGenre(deletingBookGenre);
                    Book savedBook = bookRepository.save(book);
                    return BookMapper.toDTO(savedBook);
                }
            }
        }
        return null;
    }

    @Transactional // Without the @Transactional annotation, JPA doesn’t open a transaction and
                   // therefore cannot perform write operations like remove() or delete.
    public boolean deleteBook(String isdn) {
        if (bookRepository.existsById(isdn)) {
            bookRepository.deleteById(isdn);
            return true;
        }
        return false;
    }
    
}