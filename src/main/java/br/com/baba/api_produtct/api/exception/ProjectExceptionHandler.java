package br.com.baba.api_produtct.api.exception;

import br.com.baba.api_produtct.api.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleGenericException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO(e.getMessage()));
    }
}
