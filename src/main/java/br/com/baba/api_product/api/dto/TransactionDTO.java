package br.com.baba.api_product.api.dto;

import br.com.baba.api_product.api.enums.OperationTypeEnum;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionDTO(@NotNull Long userId, @NotNull Long productId, @NotNull BigDecimal quantity, @NotNull
OperationTypeEnum operation) {
}
