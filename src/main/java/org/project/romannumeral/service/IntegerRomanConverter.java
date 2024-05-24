package org.project.romannumeral.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class IntegerRomanConverter implements Converter {

    private static final int[] values = {
            1000,
            900, 500, 400, 100,
            90, 50, 40, 10,
            9, 5, 4, 1
    };

    private static final String[] romanNumerals = {
            "M",
            "CM", "D", "CD", "C",
            "XC", "L", "XL", "X",
            "IX", "V", "IV", "I"
    };

    @Override
    public List<String> convert(Integer from, Integer to) {
        return IntStream.rangeClosed(from, to)
                .boxed()
                .map(this::integerConverter)
                .toList();
    }

    /**
     * INTEGER TO ROMAN NUMERAL CONVERTOR
     * This method uses a greedy algorithm to convert an Integer into a Roman Numeral.
     * It receives the integer as a parameter and will use a list of values
     * and their associated roman numeral to perform the conversion.
     * The approach is to start with the largest number from the list that fits the number and repeatedly
     * subtract it while filling the output with the associated Roman Numeral. Once a number no longer
     * fits, we move to the next smaller number from the list, until the entire input has been converted.
     * The time and space complexity of this algorithm is O(1).
     *
     * @param num - the integer number to be converted into a roman numeral
     * @return - a string representing the roman numeral associated with the input
     */
    private String integerConverter(int num) {
        log.debug("Received request for {}", num);
        StringBuilder sb = new StringBuilder();
        // Check every symbol and stop when num argument becomes 0.
        for (int i = 0; i < values.length && num > 0; i++) {
            // Repeat current symbol while it still fits into num
            while (values[i] <= num) {
                num -= values[i];
                sb.append(romanNumerals[i]);
                log.debug("Appended {}" , romanNumerals[i]);
            }
        }
        // Return the converted number
        return sb.toString();
    }

}
