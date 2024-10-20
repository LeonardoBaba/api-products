package br.com.baba.api_produtct.api.dto;

import br.com.baba.api_produtct.api.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDetailDTO(Long id, String name, BigDecimal price, BigDecimal quantity,
                               LocalDate registrationDate) {

    public ProductDetailDTO(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getRegistrationDate());
    }
}
