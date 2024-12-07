package com.yuina.survey.controller;

import com.yuina.survey.vo.BasicRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BasicRes> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessages.append(error.getDefaultMessage())
        );

        BasicRes response = new BasicRes(400, errorMessages.toString().trim());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicRes> handleGeneralExceptions(Exception ex) {
        BasicRes response = new BasicRes(500, "伺服器發生錯誤，請稍後再試");
        return ResponseEntity.internalServerError().body(response);
    }
}
