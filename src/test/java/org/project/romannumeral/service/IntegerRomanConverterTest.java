package org.project.romannumeral.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DirtiesContext
@SpringBootTest(classes = {IntegerRomanConverter.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegerRomanConverterTest {

    @Autowired
    private IntegerRomanConverter integerRomanConverter;

    @Test
    @Order(1)
    void givenIntegerRange_WhenConverting_thenReturnRomanNumeralsArray() {
        int from = 1;
        int to = 5;

        List<String> expected = List.of("I","II","III","IV","V");

        assertEquals(expected, integerRomanConverter.convert(from, to));
    }

    @Test
    @Order(2)
    void givenPreviousExecution_WhenCheckingCache_thenReturnModifiedCache() {
        assertEquals(5, integerRomanConverter.getCache().size());
    }

}