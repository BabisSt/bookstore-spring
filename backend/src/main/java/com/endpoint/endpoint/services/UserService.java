package com.endpoint.endpoint.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.dto.UserDTO;
import com.endpoint.endpoint.mapper.UserMapper;
import com.endpoint.endpoint.model.User;
import com.endpoint.endpoint.repositories.OrderRepository;
import com.endpoint.endpoint.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
    };

    /*
     * The purpose of this function is to get all the users via the findAll()
     * function from the repository then feed it through a stream , change them to
     * DTO and return them as a list. This way I don't expose
     * the password of the user.
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(u -> UserMapper.toDTO(u)).collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(u -> UserMapper.toDTO(u)) // applies toDTO only if user is present
                .orElse(null); // returns null if user is not found
    }

    public UserDTO getUserByFirstName(String firstName) {
        User user = userRepository.findByFirstName(firstName);
        return UserMapper.toDTO(user);
    }

    public UserDTO getUserByLastName(String lastName) {
        User user = userRepository.findByLastName(lastName);
        return UserMapper.toDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserMapper.toDTO(user);
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("This user already exists");
        }
        // Encrypt pass
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserDTO updatePassword(String email, String oldPassword, String newPassword) {

        if (userRepository.findByEmail(email) == null)
            return null;

        User user = userRepository.findByEmail(email);

        if (!user.getPassword().equalsIgnoreCase(oldPassword))
            return null;

        user.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = userRepository.save(user); // Save the entity
        return UserMapper.toDTO(updatedUser); // Return a DTO

    }

    public UserDTO updateAboutSection(String email, String newAboutSection) {
        if (userRepository.findByEmail(email) == null)
            return null;

        User user = userRepository.findByEmail(email);

        user.setAboutSection(newAboutSection);
        User updatedUser = userRepository.save(user); // Save the entity
        return UserMapper.toDTO(updatedUser); // Return a DTO
    }

    @Transactional // Without the @Transactional annotation, JPA doesn’t open a transaction and
                   // therefore cannot perform write operations like remove() or delete.
    public boolean deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            orderRepository.deleteByUserId(id);
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
