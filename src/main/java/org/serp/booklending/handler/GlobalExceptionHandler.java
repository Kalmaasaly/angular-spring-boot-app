package org.serp.booklending.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(LockedException.class)
        public ResponseEntity<ExceptionResponse> handleException(LockedException exception) {
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(
                                                ExceptionResponse.builder()
                                                                .errorCode(ErrorCode.ACCOUNT_LOCKED.getCode())
                                                                .description(ErrorCode.ACCOUNT_LOCKED.getDescription())
                                                                .error(exception.getMessage())
                                                                .build());
        }

        @ExceptionHandler(DisabledException.class)
        public ResponseEntity<ExceptionResponse> handleException(DisabledException exception) {
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(
                                                ExceptionResponse.builder()
                                                                .errorCode(ErrorCode.ACCOUNT_DISABLED.getCode())
                                                                .description(ErrorCode.ACCOUNT_DISABLED
                                                                                .getDescription())
                                                                .error(exception.getMessage())
                                                                .build());
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exception) {
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(
                                                ExceptionResponse.builder()
                                                                .errorCode(ErrorCode.BAD_CREDENTIAL.getCode())
                                                                .description(ErrorCode.BAD_CREDENTIAL.getDescription())
                                                                .error(ErrorCode.BAD_CREDENTIAL.getDescription())
                                                                .build());
        }

        @ExceptionHandler(MessagingException.class)
        public ResponseEntity<ExceptionResponse> handleException(MessagingException exception) {
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(
                                                ExceptionResponse.builder()
                                                                .error(exception.getMessage())
                                                                .build());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exception) {
                Set<String> errors = new HashSet<>();
                exception.getBindingResult().getAllErrors().forEach(error -> {
                        var errorMessage = error.getDefaultMessage();
                        errors.add(errorMessage);
                } );
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(
                                                ExceptionResponse.builder()
                                                                .validationErrors(errors)
                                                                .build());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
                exception.printStackTrace();
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(
                                                ExceptionResponse.builder()
                                                                .description("Internal server Error")
                                                                .error(exception.getMessage())
                                                                .build());
        }

}
