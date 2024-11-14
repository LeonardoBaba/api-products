package br.com.baba.api_product.api.validation.transaction;

import br.com.baba.api_product.api.model.Transaction;

public interface TransactionValidation {
    void validate(Transaction transaction);
}
