package TP_Final.devhire.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<String> userNotFoundHandler(DeveloperNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> commentNotFoundHandler(CommentNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(ExistingEmailException.class)
    public ResponseEntity<String> existingEmailHandler(ExistingEmailException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeExceptionHandler(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
