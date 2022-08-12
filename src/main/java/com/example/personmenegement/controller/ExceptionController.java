package com.example.personmenegement.controller;

import com.example.personmenegement.dto.ErrorDetails;
import com.example.personmenegement.exeption.ManyTasksException;
import com.example.personmenegement.exeption.PersonNotFoundException;
import com.example.personmenegement.exeption.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<?> handlePersonNotFoundException(PersonNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(LocalDate.now())
                .status(HttpStatus.BAD_REQUEST.value())// todo смотри туду в rest а лучше после правок rest сделай мерж сюда, чтобы исправления влились и не приходилось делать одну и ту же работу несколько раз
                .message(exception.getMessage())
                .details(request.getDescription(false)).build();
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundExceptionException(TaskNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(LocalDate.now())
                .status(HttpStatus.BAD_REQUEST.value())// todo смотри туду в rest
                .message(exception.getMessage())
                .details(request.getDescription(false)).build();
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ManyTasksException.class)
    public ResponseEntity<?> handleManyTasksExceptionException(ManyTasksException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(LocalDate.now())
                .status(HttpStatus.BAD_REQUEST.value())// todo смотри туду в rest
                .message(exception.getMessage())
                .details(request.getDescription(false)).build();
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}