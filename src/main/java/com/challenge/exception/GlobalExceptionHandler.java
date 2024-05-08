package com.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidCoodinatePointsException.class)
    public ResponseEntity<ProblemDetail> invalidCoordinatePointsExceptionHandler(InvalidCoodinatePointsException ex) {
        return this.generateProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDistanceValueException.class)
    public ResponseEntity<ProblemDetail> invalidDistanceValueException(InvalidDistanceValueException ex) {
        return this.generateProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ProblemDetail> generateProblemDetail(HttpStatus status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }
}
