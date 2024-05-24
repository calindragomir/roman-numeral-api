package org.project.romannumeral.api;

import lombok.extern.slf4j.Slf4j;
import org.project.romannumeral.exception.InvalidRangeException;
import org.project.romannumeral.model.ErrorFieldsResponse;
import org.project.romannumeral.model.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ErrorHandlerControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorFieldsResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("{} errors found: {}", e.getErrorCount(), e.getMessage());
        return ResponseEntity.badRequest().body(handleError(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessageResponse> handleBadArguments(HttpMessageNotReadableException e) {
        log.error("{} errors found: {}", 1, e.getMessage());
        return ResponseEntity.badRequest().body(handleErrorMessage(e));
    }

    @ExceptionHandler(InvalidRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorFieldsResponse> handleInvalidRange(InvalidRangeException e) {
        log.error("{} errors found: {}", 1, e.getMessage());
        return ResponseEntity.badRequest().body(handleError(e));
    }

    private static ErrorFieldsResponse handleError(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                err -> errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildErrorFieldsResponse(errors);
    }

    private static ErrorFieldsResponse handleError(InvalidRangeException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put(e.getFieldName(), e.getMessage());
        return buildErrorFieldsResponse(errors);
    }

    private static ErrorFieldsResponse buildErrorFieldsResponse(Map<String, String> errors) {
        return ErrorFieldsResponse.builder()
                .errorCount(errors.size())
                .errorFields(errors)
                .build();
    }

    private static ErrorMessageResponse handleErrorMessage(HttpMessageNotReadableException e) {
        return ErrorMessageResponse.builder()
                .errorMessage(e.getMessage())
                .build();
    }

}
