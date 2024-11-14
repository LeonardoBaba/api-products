package br.com.baba.api_product.api.service;

import br.com.baba.api_product.api.dto.TransactionDTO;
import br.com.baba.api_product.api.enums.OperationTypeEnum;
import br.com.baba.api_product.api.exception.ValidationException;
import br.com.baba.api_product.api.model.Product;
import br.com.baba.api_product.api.model.Transaction;
import br.com.baba.api_product.api.model.User;
import br.com.baba.api_product.api.repository.TransactionRepository;
import br.com.baba.api_product.api.validation.transaction.TransactionValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private Product product;

    @Mock
    private User user;
    @Mock
    private TransactionDTO transactionDTO;

    @Mock
    private Transaction transaction;

    @Spy
    private List<TransactionValidation> validations = new ArrayList<>();

    @Mock
    private TransactionValidation validation1;


    @Captor
    private ArgumentCaptor<Transaction> transactionArgumentCaptor;

    @Test
    void shouldSaveTransaction() {
        transactionDTO = new TransactionDTO(1L, 1L, BigDecimal.TEN, OperationTypeEnum.INPUT);
        when(productService.findById(transactionDTO.productId())).thenReturn(product);
        when(userService.getUserById(transactionDTO.userId())).thenReturn(user);
        when(product.getQuantity()).thenReturn(BigDecimal.ZERO);

        transactionService.transaction(transactionDTO);

        then(transactionRepository).should().save(transactionArgumentCaptor.capture());
    }

    @Test
    void shouldUseValidations() {
        transactionDTO = new TransactionDTO(1L, 1L, BigDecimal.TEN, OperationTypeEnum.INPUT);
        when(productService.findById(transactionDTO.productId())).thenReturn(product);
        when(userService.getUserById(transactionDTO.userId())).thenReturn(user);
        when(product.getQuantity()).thenReturn(BigDecimal.ZERO);
        validations.add(validation1);

        transactionService.transaction(transactionDTO);

        then(transactionRepository).should().save(transactionArgumentCaptor.capture());
        transaction = transactionArgumentCaptor.getValue();
        then(validation1).should().validate(transaction);
    }

    @Test
    void shouldThrowValidationException() {
        transactionDTO = new TransactionDTO(1L, 1L, BigDecimal.TEN, OperationTypeEnum.OUTPUT);
        when(productService.findById(transactionDTO.productId())).thenReturn(product);
        when(userService.getUserById(transactionDTO.userId())).thenReturn(user);
        validations.add(validation1);

        doThrow(new ValidationException("Insufficient product quantity in stock."))
                .when(validation1).validate(any(Transaction.class));

        assertThrows(ValidationException.class, () -> transactionService.transaction(transactionDTO));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}