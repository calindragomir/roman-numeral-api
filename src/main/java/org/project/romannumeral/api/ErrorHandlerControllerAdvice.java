package org.project.romannumeral.api;

import lombok.extern.slf4j.Slf4j;
import org.project.openapi.dto.ErrorMessageResponse;
import org.project.romannumeral.exception.InvalidRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ErrorHandlerControllerAdvice {

    private static final String ERROR_MSG_FORMAT = "Field `%s` has an error: %s";

    /**
     * Exception handler for cases when constraints are violated
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("{} errors found: {}", e.getErrorCount(), e.getMessage());
        return ResponseEntity.badRequest().body(handleError(e));
    }

    /**
     * Exception handler for cases when arguments are not integers
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessageResponse> handleBadArguments(HttpMessageNotReadableException e) {
        log.error("{} errors found: {}", 1, e.getMessage());
        return ResponseEntity.badRequest().body(handleError(e));
    }

    /**
     * Exception handler for invalid ranges provided
     */
    @ExceptionHandler(InvalidRangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessageResponse> handleInvalidRange(InvalidRangeException e) {
        log.error("{} errors found: {}", 1, e.getMessage());
        return ResponseEntity.badRequest().body(handleError(e));
    }

    private static ErrorMessageResponse handleError(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(
                err -> errors.add(
                        ERROR_MSG_FORMAT.formatted(err.getField(), err.getDefaultMessage())
                )
        );
        return createErrorResponse(errors);
    }

    private static ErrorMessageResponse handleError(HttpMessageNotReadableException e) {
        return createErrorResponse(List.of(e.getMessage()));
    }

    private static ErrorMessageResponse handleError(InvalidRangeException e) {
        String errorMessage = ERROR_MSG_FORMAT.formatted(
                e.getFieldName(), e.getMessage());
        return createErrorResponse(List.of(errorMessage));
    }

    private static ErrorMessageResponse createErrorResponse(List<String> errors) {
        return new ErrorMessageResponse()
                .errorCount(errors.size())
                .errors(errors);
    }

}
