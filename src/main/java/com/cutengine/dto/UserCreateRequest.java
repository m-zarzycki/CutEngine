package com.cutengine.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "Imię jest wymagane")
        @Size(min = 2, max = 50, message = "Imię musi mieć od 2 do 50 znaków")
        String firstName,

        @NotBlank(message = "Nazwisko jest wymagane")
        @Size(min = 2, max = 50, message = "Nazwisko musi mieć od 2 do 50 znaków")
        String lastName,

        @NotBlank(message = "Nazwa użytkownika jest wymagana")
        @Size(min = 3, max = 32, message = "Nazwa użytkownika musi mieć od 3 do 32 znaków")
        String username,

        @NotBlank(message = "Hasło jest wymagane")
        @Size(min = 8, max = 72, message = "Hasło musi mieć od 8 do 72 znaków")
        String password,

        @NotBlank(message = "Email jest wymagany")
        @Email(message = "Nieprawidłowy format adresu e-mail")
        @Size(max = 50, message = "Email może mieć maks. 50 znaków")
        String email,

        @Size(max = 20, message = "Numer telefonu może mieć maks. 20 znaków")
        @Pattern(regexp = "^\\+?[0-9]{1,4}?(?:\\s?[0-9]){8,14}$", message = "Niepoprawny format numeru telefonu")
        String phone
) {
        @Override
        public String toString() {
                return "UserCreateRequest[username=%s, email=%s]".formatted(username, email);
        }
}
