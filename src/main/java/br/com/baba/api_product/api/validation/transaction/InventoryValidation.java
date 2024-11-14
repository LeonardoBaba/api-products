package br.com.baba.api_product.api.validation.transaction;

import br.com.baba.api_product.api.enums.OperationTypeEnum;
import br.com.baba.api_product.api.exception.ValidationException;
import br.com.baba.api_product.api.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InventoryValidation implements TransactionValidation {
    @Override
    public void validate(Transaction transaction) {
        if (transaction.getOperationType().equals(OperationTypeEnum.OUTPUT) &&
                transaction.getProduct().getQuantity()
                        .subtract(transaction.getQuantity()).compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Insufficient product quantity in stock.");
        }
    }
}
