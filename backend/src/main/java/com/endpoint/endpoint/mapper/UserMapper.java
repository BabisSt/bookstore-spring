package com.endpoint.endpoint.mapper;

import java.util.Optional;

import com.endpoint.endpoint.dto.UserDTO;
import com.endpoint.endpoint.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getAboutSection());
    }

    public static Optional<UserDTO> OptionaltoDTO(Optional<User> user) {
        if (user == null) {
            return null;
        }

        User u = user.get();
        return Optional.of(new UserDTO(u.getFirstName(), u.getLastName(), u.getEmail(), u.getAboutSection()));
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), null,
                userDTO.getAboutSection());
    }
}
