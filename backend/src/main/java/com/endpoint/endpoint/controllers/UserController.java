package com.endpoint.endpoint.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.endpoint.endpoint.dto.UserDTO;
import com.endpoint.endpoint.model.User;

import com.endpoint.endpoint.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController() {
    };

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/firstName/{firstName}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDTO getUserByFirstName(@PathVariable String firstName) {
        return userService.getUserByFirstName(firstName);
    }

    @GetMapping("/lastName/{lastName}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDTO getUserByLastName(@PathVariable String lastName) {
        return userService.getUserByLastName(lastName);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/updatePassword/{email}/{oldPassword}/{newPassword}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDTO updatePassword(@PathVariable("email") String email,
            @PathVariable("oldPassword") String oldPassword, @PathVariable("newPassword") String newPassword) {
        return userService.updatePassword(email, oldPassword, newPassword);
    }

    @PutMapping("/updateAboutSection/{email}/{newAboutSection}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDTO updateAboutSection(@PathVariable("email") String email,
            @PathVariable("newAboutSection") String newAboutSection) {
        return userService.updateAboutSection(email, newAboutSection);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
