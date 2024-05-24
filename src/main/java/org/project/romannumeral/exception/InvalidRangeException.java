package org.project.romannumeral.exception;

import lombok.Getter;

@Getter
public class InvalidRangeException extends RuntimeException {

    private final String fieldName;

    public InvalidRangeException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

}
