package org.project.romannumeral.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/convert")
public class RomanNumeralController {

    @PostMapping("/integers")
    public ResponseEntity<String> convertIntegersToRoman() {
        return ResponseEntity.ok("hello world");
    }

}
