package br.com.baba.api_produtct.api.dto;

import br.com.baba.api_produtct.api.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserFormDTO(
        @NotBlank
        @Size(max = 10)
        String username,

        @NotBlank
        @Size(max = 10)
        String name,

        @NotNull
        RoleEnum role,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password) {
}
