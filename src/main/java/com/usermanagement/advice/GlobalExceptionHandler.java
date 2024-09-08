package com.usermanagement.advice;

import com.usermanagement.common.ResponseWrapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.usermanagement.util.CommonUtils.responseTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseWrapper<Map<String, String>>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        final Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity
                .internalServerError()
                .body(ResponseWrapper.<Map<String, String>>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errors(List.of(errorMap))
                        .responsetime(responseTime())
                        .build());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ResponseWrapper<Map<String, String>>> handleBusinessException(UsernameNotFoundException ex) {
        final Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(ResponseWrapper.<Map<String, String>>builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errors(List.of(errorMap))
                        .responsetime(responseTime())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseWrapper<?>> runtimeException(RuntimeException ex) {
        return ResponseEntity.internalServerError().body(ResponseWrapper
                .builder()
                .errors(List.of(ServerError.builder().error(ex.getMessage()).build()))
                .responsetime(responseTime())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage()).build());
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ResponseWrapper<?>> badCredential(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseWrapper
                .builder()
                .errors(List.of(ServerError.builder().error(ex.getMessage()).build()))
                .status(HttpStatus.UNAUTHORIZED.value())
                .responsetime(responseTime())
                .message(ex.getMessage()).build());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResponseWrapper<?>> accessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseWrapper
                .builder()
                .errors(List.of(ServerError.builder().error(ex.getMessage()).build()))
                .status(HttpStatus.FORBIDDEN.value())
                .responsetime(responseTime())
                .message(ex.getMessage()).build());
    }

    @ExceptionHandler({UnsupportedJwtException.class, MalformedJwtException.class, SignatureException.class, ExpiredJwtException.class})
    public ResponseEntity<ResponseWrapper<?>> jwtValidation(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseWrapper
                .builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .errors(List.of(ServerError.builder().error(ex.getMessage()).build()))
                .responsetime(responseTime())
                .build());
    }
}
