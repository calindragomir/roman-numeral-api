package org.project.romannumeral;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.romannumeral.model.IntegerRangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RomanNumeralApplicationTests {

	private static final String CONTENT_TYPE = "application/json";
	private static final String APP_MAIN_ROUTE = "/api/v1/convert/integers";

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void validateBaseCaseWorks() throws Exception {
		IntegerRangeRequest request = IntegerRangeRequest.builder()
				.from(1)
				.to(5)
				.build();

		List<String> expectedResponse = List.of("I","II","III","IV","V");

		mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(CONTENT_TYPE)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
	}

}
