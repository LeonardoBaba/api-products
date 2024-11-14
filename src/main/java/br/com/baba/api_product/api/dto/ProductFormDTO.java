package br.com.baba.api_product.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductFormDTO(@NotBlank String name, @NotNull BigDecimal price, BigDecimal quantity) {
}
