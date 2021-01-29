package dev.hoon.basic.global.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 필요시 각 Exception별 정의
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception e) {

        logger.error("Failed : {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
