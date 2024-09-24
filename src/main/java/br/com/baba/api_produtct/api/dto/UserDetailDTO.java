package br.com.baba.api_produtct.api.dto;

import br.com.baba.api_produtct.api.enums.RoleEnum;
import br.com.baba.api_produtct.api.model.User;

import java.time.LocalDate;

public record UserDetailDTO(Long id, String username, String name, RoleEnum role, String email, LocalDate creationDate) {

    public UserDetailDTO(User user){
        this(user.getId(), user.getUsername(), user.getName(), user.getRole(), user.getEmail(), user.getCreationDate());
    }
}
