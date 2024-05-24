package org.project.romannumeral.validators;

import org.project.romannumeral.exception.InvalidRangeException;

public class RangeValidator {

    public static void validateRange(Integer from, Integer to) {
        if (from > to) {
            throw new InvalidRangeException(
                    "from",
                    "value %d cannot be higher than <TO> value %d".formatted(from, to)
            );
        }
    }

}
