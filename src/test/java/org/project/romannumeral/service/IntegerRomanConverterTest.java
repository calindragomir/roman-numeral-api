package org.project.romannumeral.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(classes = {IntegerRomanConverter.class})
public class IntegerRomanConverterTest {

    @Autowired
    private IntegerRomanConverter integerRomanConverter;

    @Test
    void checkConversionIsExecutedCorrectly() {
        int from = 1;
        int to = 5;

        List<String> expected = List.of("I","II","III","IV","V");

        assertEquals(expected, this.integerRomanConverter.convert(from, to));
    }

}