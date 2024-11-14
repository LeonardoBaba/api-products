package br.com.baba.api_product.api.dto;

import org.springframework.validation.FieldError;

public record FieldErrorDTO(String campo, String message) {
    public FieldErrorDTO(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
