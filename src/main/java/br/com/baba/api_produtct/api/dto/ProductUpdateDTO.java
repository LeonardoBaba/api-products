package br.com.baba.api_produtct.api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductUpdateDTO(@NotNull Long id, @NotNull BigDecimal price) {
}
