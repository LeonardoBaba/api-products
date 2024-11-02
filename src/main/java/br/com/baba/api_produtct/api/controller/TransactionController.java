package br.com.baba.api_produtct.api.controller;

import br.com.baba.api_produtct.api.dto.TransactionDTO;
import br.com.baba.api_produtct.api.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/transaction")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @Transactional
    public ResponseEntity transaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        transactionService.transaction(transactionDTO);
        return ResponseEntity.ok().build();
    }

}
