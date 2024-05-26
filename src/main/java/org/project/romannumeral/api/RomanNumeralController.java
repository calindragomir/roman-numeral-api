package org.project.romannumeral.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.openapi.api.ConvertApiDelegate;
import org.project.openapi.dto.IntegerRangeRequest;
import org.project.romannumeral.service.Converter;
import org.project.romannumeral.validators.RangeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class RomanNumeralController implements ConvertApiDelegate {

    @Autowired
    private final Converter converter;

    /**
     * Convert a range of integers into Roman Numerals.
     *
     * @param rangeRequest {@link IntegerRangeRequest} object that contains `from` and `to` integer range
     * @return
     *  ResponseEntity with status code 200 (OK) and a string array of roman numerals obtained after conversion
     *  ResponseEntity with status code 400 and detailed error information if invalid numbers are given
     */

    @Override
    public ResponseEntity<List<String>> convertIntegerToRoman(IntegerRangeRequest rangeRequest) {
        log.info("Got a request for range between {} and {}", rangeRequest.getFrom(), rangeRequest.getTo());

        RangeValidator.validateRange(rangeRequest.getFrom(), rangeRequest.getTo());
        List<String> converted = converter.convert(rangeRequest.getFrom(), rangeRequest.getTo());

        return ResponseEntity.ok(converted);
    }
}
