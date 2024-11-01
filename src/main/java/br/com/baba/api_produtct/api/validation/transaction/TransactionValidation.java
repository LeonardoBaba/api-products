package br.com.baba.api_produtct.api.validation.transaction;

import br.com.baba.api_produtct.api.model.Transaction;

public interface TransactionValidation {
    void validate(Transaction transaction);
}
