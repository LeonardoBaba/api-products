package br.com.baba.api_product.api.model;

import br.com.baba.api_product.api.dto.TransactionDTO;
import br.com.baba.api_product.api.enums.OperationTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private OperationTypeEnum operationType;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Transaction(Product product, User user, TransactionDTO transactionDTO) {
        this.quantity = transactionDTO.quantity();
        this.operationType = transactionDTO.operation();
        this.transactionDate = LocalDate.now();
        this.product = product;
        this.user = user;
    }
}
