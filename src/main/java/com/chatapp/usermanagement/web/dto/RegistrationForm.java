package com.chatapp.usermanagement.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public record RegistrationForm(

        @NotNull @NotEmpty @NotBlank @Length(min = 5, max = 100,
                message = "Username must be between 5 and 100 characters")
        String username,


        @NotNull @NotEmpty @NotBlank
        String password,

        @Length(max = 250, message = "First name must be less than 100 characters")
        String firstName,


        @Length(max = 250, message = "Last name must be less than 100 characters")
        String lastName,

        @Email
        String email,

        Date dateOfBirth,

        String roles
) {
}
