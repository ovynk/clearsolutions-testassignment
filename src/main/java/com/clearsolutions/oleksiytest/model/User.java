package com.clearsolutions.oleksiytest.model;

import com.clearsolutions.oleksiytest.annotation.ValidAge;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    @NotBlank
    @Email(message = "Not valid email")
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @ValidAge
    @Past(message = "Date must be earlier than current date")
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

}
