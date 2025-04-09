package ru.fedorov.knigoed.dto;

import jakarta.validation.constraints.*;

public record BookDto(
        Long id,

        @NotBlank(message = "Vendor code cannot be blank")
        String vendorCode,

        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotNull(message = "Year cannot be null")
        @Min(value = 1800, message = "Year must be at least 1800")
        Integer year,

        @NotBlank(message = "Brand cannot be blank")
        String brand,

        @NotNull(message = "Stock cannot be null")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stock,

        @NotNull(message = "Price cannot be null")
        @DecimalMin(value = "0.0", message = "Price cannot be negative")
        Double price
) {
    public BookDto {
        if (year != null && year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year cannot be in the future");
        }
    }
}
