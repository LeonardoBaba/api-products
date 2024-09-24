package br.com.baba.api_produtct.api.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String username, @NotBlank String password) {
}
