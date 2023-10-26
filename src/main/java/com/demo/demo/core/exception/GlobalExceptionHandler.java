package com.demo.demo.core.exception;

import com.demo.demo.response.ApplicationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApplicationError> badRequestHandler(BadRequestException e, WebRequest request) {
        return new ResponseEntity<>(
                ApplicationError.builder()
                        .message(e.getMessage())
                        .request(request)
                        .status(HttpStatus.BAD_REQUEST)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ApplicationError> dataNotFoundException(DataNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(
                ApplicationError.builder()
                        .message(e.getMessage())
                        .request(request)
                        .status(HttpStatus.NOT_FOUND)
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataAlreadyExistException.class})
    public ResponseEntity<ApplicationError> dataAlreadyExistException(DataAlreadyExistException e, WebRequest request) {
        return new ResponseEntity<>(
                ApplicationError.builder()
                        .message(e.getMessage())
                        .request(request)
                        .status(HttpStatus.BAD_REQUEST)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidValidationException.class})
    public ResponseEntity<ApplicationError> invalidValidationException(InvalidValidationException e, WebRequest request) {
        return new ResponseEntity<>(
                ApplicationError.builder()
                        .message(e.getMessage())
                        .request(request)
                        .status(HttpStatus.BAD_REQUEST)
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnknownDataBaseException.class})
    public ResponseEntity<ApplicationError> unknownDataBaseException(UnknownDataBaseException e, WebRequest request) {
        return new ResponseEntity<>(
                ApplicationError.builder()
                        .message(e.getMessage())
                        .request(request)
                        .status(HttpStatus.CONFLICT)
                        .build(),
                HttpStatus.CONFLICT);
    }
}
