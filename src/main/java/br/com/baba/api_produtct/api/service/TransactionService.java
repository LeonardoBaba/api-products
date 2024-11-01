package br.com.baba.api_produtct.api.service;

import br.com.baba.api_produtct.api.dto.TransactionDTO;
import br.com.baba.api_produtct.api.model.Transaction;
import br.com.baba.api_produtct.api.repository.TransactionRepository;
import br.com.baba.api_produtct.api.validation.transaction.TransactionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private List<TransactionValidation> validations;

    public void transaction(TransactionDTO transactionDTO) {
        var product = productService.findById(transactionDTO.productId());
        var user = userService.getUserById(transactionDTO.userId());
        var transaction = new Transaction(product, user, transactionDTO);
        validations.forEach(validation -> validation.validate(transaction));
        product.setQuantity(transaction.getOperationType().apply(product.getQuantity(), transactionDTO.quantity()));
        repository.save(transaction);
    }
}
