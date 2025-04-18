package br.com.baba.api_product.api.exception;

import br.com.baba.api_product.api.dto.ErrorResponseDTO;
import br.com.baba.api_product.api.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleGenericException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity handleValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(FieldErrorDTO::new).toList());
    }

    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler
    public ResponseEntity handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
