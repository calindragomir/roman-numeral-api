package org.project.romannumeral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.project.romannumeral", "org.project.openapi"})
public class RomanNumeralApplication {

	public static void main(String[] args) {
		SpringApplication.run(RomanNumeralApplication.class, args);
	}

}
