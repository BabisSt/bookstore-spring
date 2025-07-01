package com.endpoint.endpoint.controllers;

import org.springframework.web.bind.annotation.RestController;


import com.endpoint.endpoint.model.Author;
import com.endpoint.endpoint.services.AuthorService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<Author> getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAuthor(@RequestBody @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.ok(createdAuthor);
    }
    //Requires the whole Author entity to be sent in order to verify it
    @PutMapping("/updateAboutSection/{id}/{newAboutSection}")
    @PreAuthorize("hasRole('ADMIN')")
    public Author updateAuthorAboutSection(@PathVariable("id") Integer id, @PathVariable("newAboutSection") String newAboutSection,
            @RequestBody @Valid Author author) {
        return authorService.updateAuthorAboutSection(id, newAboutSection, author);
    }

  
}
