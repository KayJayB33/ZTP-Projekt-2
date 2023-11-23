package pl.edu.pk.ztpprojekt2.controller;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "Name may not be null")
        @NotBlank(message = "Name may not be blank")
        @NotEmpty(message = "Name may not be empty")
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        String name,
        @NotNull(message = "Description may not be null")
        @NotBlank(message = "Description may not be blank")
        @NotEmpty(message = "Description may not be empty")
        String description,
        @NotNull(message = "Price may not be null")
        @Positive(message = "Price must be positive")
        BigDecimal price,
        @NotNull(message = "Available quantity may not be null")
        @PositiveOrZero(message = "Available quantity me greater than or equals to 0")
        int availableQuantity) {}
