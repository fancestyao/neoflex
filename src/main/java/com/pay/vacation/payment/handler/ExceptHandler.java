package com.pay.vacation.payment.handler;

import com.pay.vacation.payment.exceptions.NegativeNumberException;
import com.pay.vacation.payment.exceptions.WrongDateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptHandler {
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> validationError(final NegativeNumberException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Ошибка валидации.", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> validationError(final WrongDateException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(Map.of("Ошибка переданной даты.", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> serverError(final Throwable e) {
        return new ResponseEntity<>(Map.of("Ошибка сервера.", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
