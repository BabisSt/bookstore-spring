package com.endpoint.endpoint.dto;

import com.endpoint.endpoint.model.Person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO extends Person {

    @NotBlank
    @Email
    private String email;

    public UserDTO() {
    };

    public UserDTO(String firstName, String lastName, String email, String aboutSection) {
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.email = email;
        super.setAboutSection(aboutSection);

    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
