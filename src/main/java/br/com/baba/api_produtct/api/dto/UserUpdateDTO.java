package br.com.baba.api_produtct.api.dto;

import br.com.baba.api_produtct.api.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(@NotNull Long id, @NotBlank String name, @NotBlank String email, @NotNull RoleEnum role) {
}
