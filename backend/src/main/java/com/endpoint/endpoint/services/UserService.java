package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.dto.UserDTO;
import com.endpoint.endpoint.mapper.UserMapper;
import com.endpoint.endpoint.model.User;
import com.endpoint.endpoint.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

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
        if (!user.isNew()) {
            throw new IllegalArgumentException("This user already exists");
        }
        return userRepository.save(user);
    }

    public boolean deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
