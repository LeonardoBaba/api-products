package br.com.baba.api_produtct.api.validation.transaction;

import br.com.baba.api_produtct.api.enums.OperationTypeEnum;
import br.com.baba.api_produtct.api.exception.ValidationException;
import br.com.baba.api_produtct.api.model.Product;
import br.com.baba.api_produtct.api.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryValidationTest {

    @Mock
    private Transaction transaction;

    @Mock
    private Product product;

    @InjectMocks
    private InventoryValidation inventoryValidation;

    @Test
    void shouldNotThrowException() {
        when(transaction.getOperationType()).thenReturn(OperationTypeEnum.OUTPUT);
        when(transaction.getProduct()).thenReturn(product);
        when(product.getQuantity()).thenReturn(BigDecimal.TEN);
        when(transaction.getQuantity()).thenReturn(BigDecimal.TWO);

        Assertions.assertDoesNotThrow(() -> {
            inventoryValidation.validate(transaction);
        });
    }

    @Test
    void shouldNotThrowExceptionWhenOperationTypeIsInput() {
        when(transaction.getOperationType()).thenReturn(OperationTypeEnum.INPUT);

        Assertions.assertDoesNotThrow(() -> {
            inventoryValidation.validate(transaction);
        });
    }

    @Test
    void shouldThrowExceptionWhenOperationTypeIsOutputAndDoesNotHaveStock() {
        when(transaction.getOperationType()).thenReturn(OperationTypeEnum.OUTPUT);
        when(transaction.getProduct()).thenReturn(product);
        when(product.getQuantity()).thenReturn(BigDecimal.ZERO);
        when(transaction.getQuantity()).thenReturn(BigDecimal.TWO);

        Assertions.assertThrows(ValidationException.class, () -> {
            inventoryValidation.validate(transaction);
        });
    }

}