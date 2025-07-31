package com.endpoint.endpoint.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.endpoint.endpoint.dto.BookDTO;
import com.endpoint.endpoint.enums.BookGenre;
import com.endpoint.endpoint.model.Author;
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.repositories.AuthorRepository;
import com.endpoint.endpoint.repositories.BookRepository;
import com.endpoint.endpoint.services.BookService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;
    
    @InjectMocks
    private BookService bookService;


    @Test
    void testGetAllBooks() {
        
        Book book1 = new Book();
        book1.setIsdn("1111");
        book1.setTitle("Brave New World");

        Book book2 = new Book();
        book2.setIsdn("2222");
        book2.setTitle("Island");

        List<Book> books = List.of(book1, book2);

        // Mock repository method that finds by author ID or object
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> results = bookService.getAllBooks();

        assertEquals(2, results.size());
        assertEquals("Brave New World", results.get(0).getTitle());
        assertEquals("Island", results.get(1).getTitle());
    }

    @Test
    void testGetBooksByAuthor() {
        // Create Author
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Aldous");
        author.setLastName("Huxley");

        // Create Books and assign the same author
        Book book1 = new Book();
        book1.setIsdn("1111");
        book1.setTitle("Brave New World");
        book1.setAuthor(author);

        Book book2 = new Book();
        book2.setIsdn("2222");
        book2.setTitle("Island");
        book2.setAuthor(author);

        List<Book> books = List.of(book1, book2);

        // Mock repository method that finds by author ID or object
        when(bookRepository.findByAuthor(author)).thenReturn(Optional.of(books));

        Optional<List<BookDTO>> result = bookService.getBookByAuthor(author);

        assertTrue(result.isPresent()); // First confirm it's present
        assertEquals(2, result.get().size()); // Then call .size() on the actual List
        assertEquals("Brave New World", result.get().get(0).getTitle());
        assertEquals("Island", result.get().get(1).getTitle());
    }

    @Test
    void testGetBookByTitle() {
        Book book = new Book();
        book.setTitle("Brave New World");

        when(bookRepository.findByTitle("Brave New World")).thenReturn(Optional.of(book));

        Optional<BookDTO> result = bookService.getBookByTitle("Brave New World");

        assertTrue(result.isPresent());
        assertEquals("Brave New World", result.get().getTitle());
    }

    @Test
    void testGetBookByIsdn() {
        Book book = new Book();
        book.setIsdn("9783161484100");

        when(bookRepository.findByIsdn("9783161484100")).thenReturn(book);

        Optional<BookDTO> result = bookService.getBookByIsdn("9783161484100");

        assertTrue(result.isPresent());
        assertEquals("9783161484100", result.get().getIsdn());
    }

    @Test
    void testUpdateBookTitle() {
        Book book = new Book();
        book.setIsdn("1234567890");
        book.setTitle("Old Title");

        when(bookRepository.existsById("1234567890")).thenReturn(true); // <- This line is missing
        when(bookRepository.findByIsdn("1234567890")).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Optional<BookDTO> updated = Optional.ofNullable(bookService.updateBookTitle("1234567890", "New Title"));
        
        assertTrue(updated.isPresent());
        assertEquals("New Title", updated.get().getTitle());
    }

    @Test
    void testUpdateBookStock_success() {
        Book book = new Book();
        book.setIsdn("123");
        book.setStock(10);

        when(bookRepository.existsById("123")).thenReturn(true);
        when(bookRepository.findByIsdn("123")).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.updateBookStock("123", 20);
        assertNotNull(result);
        assertEquals(20, book.getStock());
    }

    @Test
    void testUpdateBookStock_invalidStock() {
        when(bookRepository.existsById("123")).thenReturn(true);

        BookDTO result = bookService.updateBookStock("123", -5);
        assertNull(result);
    }

    @Test
    void testUpdateBookAuthor_success() {
        Book book = new Book();
        book.setIsdn("123");
        Author author = new Author();
        author.setId(1);

        when(bookRepository.existsById("123")).thenReturn(true);
        when(bookRepository.findByIsdn("123")).thenReturn(book);
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.updateBookAuthor("123", 1);
        assertNotNull(result);
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testUpdateBookAuthor_authorNotFound() {
        when(bookRepository.existsById("123")).thenReturn(true);
        when(authorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
            () -> bookService.updateBookAuthor("123", 1));
    }

    @Test
    void testAddBookGenre_success() {
        Book book = new Book();
        book.setIsdn("123");
        BookGenre genreToAdd = BookGenre.Action;

        when(bookRepository.existsById("123")).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.addBookGenre("123", BookGenre.Action, book);
        assertNotNull(result);
        assertTrue(book.getBookGenre().contains(genreToAdd));
    }

    @Test
    void testRemoveBookGenre_success() {
        Book book = new Book();
        book.setIsdn("123");
        BookGenre genreToRemove = BookGenre.Action;
        book.addBookGenre(genreToRemove);

        when(bookRepository.existsById("123")).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.removeBookGenre("123", genreToRemove, book);
        assertNotNull(result);
        assertFalse(book.getBookGenre().contains(genreToRemove));
    }

    @Test
    void testDeleteBook_success() {
        when(bookRepository.existsById("123")).thenReturn(true);

        boolean deleted = bookService.deleteBook("123");
        assertTrue(deleted);
        verify(bookRepository).deleteById("123");
    }

    @Test
    void testDeleteBook_notExists() {
        when(bookRepository.existsById("123")).thenReturn(false);

        boolean deleted = bookService.deleteBook("123");
        assertFalse(deleted);
        verify(bookRepository, never()).deleteById(anyString());
    }

    @Test
    void testCreateBook_success() {
        // Create a mock author
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Aldous");
        author.setLastName("Huxley");


        // Create a new book with the author
        Book book = new Book();
        book.setIsdn("1234567890");
        book.setTitle("1984");
        book.setAuthor(author);

        // What the repository should return when findById is called
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(bookRepository.save(book)).thenReturn(book);

        // Call the method
        Book result = bookService.createBook(book);

        // Verify
        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        assertEquals("Aldous", result.getAuthor().getFirstName());
        verify(bookRepository, times(1)).save(book);
    }



}
