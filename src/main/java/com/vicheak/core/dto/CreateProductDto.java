package com.vicheak.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CreateProductDto(@NotBlank(message = "Product name must not be blank!")
                               String name,

                               @NotBlank(message = "Product description must not be blank!")
                               String description,

                               @NotNull(message = "Product price must not be null!")
                               @Positive(message = "Product price must be greater than zero")
                               Double price,

                               Integer categoryId) {
}
