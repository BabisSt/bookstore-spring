package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.Author;
import com.endpoint.endpoint.repositories.AuthorRepository;


@Service
public class AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Integer id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author newAuthor) {
        // if author exists then link it with the new book
        Optional<Author> existingAuthor = authorRepository.findById(newAuthor.getId());
        if (existingAuthor.isPresent()) {
            throw new IllegalArgumentException("This author already exists");
        }       

        return authorRepository.save(newAuthor);
    }

    public Author updateAuthorAboutSection(Integer id, String newAboutSection, Author author) {
       if (authorRepository.existsById(id)) {
            author.setAboutSection(newAboutSection);
            return authorRepository.save(author);
        }
        return null;
    }

}
