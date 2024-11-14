package br.com.baba.api_product.api.repository;

import br.com.baba.api_product.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
