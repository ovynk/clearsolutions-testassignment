package com.clearsolutions.oleksiytest.model;

import com.clearsolutions.oleksiytest.annotation.ValidAge;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotBlank
    @Email(message = "Not valid email")
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @ValidAge
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

}
