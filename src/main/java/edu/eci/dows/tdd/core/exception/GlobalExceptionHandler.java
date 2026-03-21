package edu.eci.dows.tdd.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse("Usuario no encontrado", ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }


    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<Map<String, Object>> handleBookNotAvailable(BookNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse("Libro no disponible", ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }


    @ExceptionHandler(LoanLimitExceededException.class)
    public ResponseEntity<Map<String, Object>> handleLoanLimit(LoanLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse("Límite de préstamos excedido", ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse("Error interno del sistema", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }


    private Map<String, Object> errorResponse(String error, String details, int status) {
        return Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", status,
                "error", error,
                "details", details
        );
    }
}
