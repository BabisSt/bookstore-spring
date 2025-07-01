package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<UserDTO> getUserById(Integer Id) {
        Optional<User> user = userRepository.findById(Id);

        return UserMapper.OptionaltoDTO(user);
    }

    public Optional<UserDTO> getUserByFirstName(String firstName) {
        Optional<User> user = userRepository.findByFirstName(firstName);

        return UserMapper.OptionaltoDTO(user);
    }

    public Optional<UserDTO> getUserByLastName(String lastName) {
        Optional<User> user = userRepository.findByLastName(lastName);

        return UserMapper.OptionaltoDTO(user);
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return UserMapper.OptionaltoDTO(user);
    }

    public User createUser(User user) {
        Optional<UserDTO> existingUser = getUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("This user already exists");
        }

        return userRepository.save(user);
    }

    public User updatePassword(Integer id, String newPassword, User user) {
        if (!userRepository.existsById(id)) {
            return null; // user does not exist
        }
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public User updateAboutSection(Integer id, String newAboutSection, User user) {
        if (!userRepository.existsById(id)) {
            return null; // user does not exist
        }
        user.setAboutSection(newAboutSection);
        return userRepository.save(user);
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
