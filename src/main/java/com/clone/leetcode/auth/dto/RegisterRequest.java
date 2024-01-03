package com.clone.leetcode.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "name cannot be blank")
        String name,
        @NotBlank(message = "email cannot be blank")
        @Email(message = "invalid email address")
        String email,
        @NotBlank(message = "password cannot be blank")
        @Size(min = 8, max = 255, message = "password should be between 8 and 255 characters")
        String password
) {
}
