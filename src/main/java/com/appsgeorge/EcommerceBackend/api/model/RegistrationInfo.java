package com.appsgeorge.EcommerceBackend.api.model;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrationInfo {

    @NotNull
    @NotBlank
    @Size(min = 5, max = 100)
    private String username;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 100)
    @Pattern(regexp= "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;
}
