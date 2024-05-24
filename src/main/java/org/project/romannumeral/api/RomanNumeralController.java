package org.project.romannumeral.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.romannumeral.model.IntegerRangeRequest;
import org.project.romannumeral.service.Converter;
import org.project.romannumeral.validators.RangeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/convert")
@AllArgsConstructor
@Validated
public class RomanNumeralController {

    @Autowired
    private final Converter converter;

    /**
     * Convert a range of integers.
     *
     * @param rangeRequest {@link IntegerRangeRequest} object that contains `from` and `to` integer range
     * @return
     *  ResponseEntity with status code 200 (OK) and with all integers converted to roman numerals
     *  ResponseEntity with status code 400 and detailed error information if invalid numbers are given
     */
    @PostMapping("/integers")
    public ResponseEntity<List<String>> convertIntegersToRoman(
            @Valid @RequestBody IntegerRangeRequest rangeRequest
    ) {
        log.info("Got a request for range between {} and {}", rangeRequest.getFrom(), rangeRequest.getTo());
        RangeValidator.validateRange(rangeRequest.getFrom(), rangeRequest.getTo());
        return ResponseEntity.ok(converter.convert(rangeRequest.getFrom(), rangeRequest.getTo()));
    }

}
