package com.macv.sprint1.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.macv.sprint1.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionsHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(e.getMessage()));
    }

    @ExceptionHandler(value = SellerNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> sellerNotFoundException(SellerNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(e.getMessage()));
    }

    @ExceptionHandler(value = JsonParseException.class)
    public ResponseEntity<ErrorResponseDto> jsonParseException(JsonParseException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto("error al convertir a json"));
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<ErrorResponseDto> numberFormatException(NumberFormatException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto("No se pudo convertir el parámetro a número"));
    }

    @ExceptionHandler(value= DateTimeParseException.class)
    public ResponseEntity<ErrorResponseDto> dateTimeParseException(DateTimeParseException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto("La fecha debe tener el formato dd-MM-yyyy"));
    }
}
