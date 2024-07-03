package com.teste.daviugtsic.errorhandling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe responsável por capturar e tratar exceções lançadas pelos controladores.
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    /**
     * Manipulador para exceções do tipo EntityNotFoundException.
     *
     * @param ex       A exceção lançada
     * @param request  O objeto WebRequest associado à solicitação
     * @return ResponseEntity com detalhes do erro e status HTTP 404
     */
    @JsonIgnore
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Entidade não localizada");
        body.put("api", "Erro na API");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Manipulador para exceções do tipo ConstraintViolationException.
     *
     * @param ex       A exceção lançada
     * @param request  O objeto WebRequest associado à solicitação
     * @return ResponseEntity com detalhes do erro e status HTTP 400
     */
    @JsonIgnore
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Erro ao validar a entrada");

        int i = 0;
        for (var e : ex.getConstraintViolations()) {
            body.put("erro" + i++, e.getMessage());
        }

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manipulador para exceções do tipo NullPointerException.
     *
     * @param ex       A exceção lançada
     * @param request  O objeto WebRequest associado à solicitação
     * @return ResponseEntity com detalhes do erro e status HTTP 500
     */
    @JsonIgnore
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(
            NullPointerException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Erro qualquer");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
