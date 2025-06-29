package com.endpoint.endpoint.mapper;

import com.endpoint.endpoint.dto.UserDTO;
import com.endpoint.endpoint.model.User;

public class UserMapper {
    
    public static UserDTO toDTO(User user){
        if(user == null){
            return null;
        }

        return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public static User toEntity(UserDTO userDTO){
        if(userDTO == null){
            return null;
        }

        return new User(userDTO.getFirstName(),userDTO.getLastName(),userDTO.getEmail(),null);
    }
}
