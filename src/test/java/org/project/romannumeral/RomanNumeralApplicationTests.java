package org.project.romannumeral;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.romannumeral.model.ErrorFieldsResponse;
import org.project.romannumeral.model.IntegerRangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RomanNumeralApplicationTests {
	private static final String APP_MAIN_ROUTE = "/api/v1/convert/integers";

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void testNormalCaseWorks() throws Exception {
		IntegerRangeRequest request = IntegerRangeRequest.builder()
				.from(1)
				.to(5)
				.build();

		List<String> expectedResponse = List.of("I","II","III","IV","V");

		mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
	}

	@Test
	void testFullRangeIsGenerated() throws Exception {
		IntegerRangeRequest request = IntegerRangeRequest.builder()
				.from(1)
				.to(3999)
				.build();

		MvcResult result = mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andReturn();

		assertEquals(3999,
				objectMapper.readTree(result.getResponse().getContentAsString()).size());
	}

	@Test()
	void testInvalidInputReturnsBadRequestWithErrorMessage() throws Exception {
		IntegerRangeRequest request = IntegerRangeRequest.builder()
				.from(-1)
				.to(4000)
				.build();

		ErrorFieldsResponse expectedError = ErrorFieldsResponse.builder()
				.errorCount(2)
				.errorFields(Map.of("from", "must be between 1 and 3999",
									"to", "must be between 1 and 3999"))
				.build();

		mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(expectedError),
									false));
	}

	@Test()
	void testInvalidRangeReturnsBadRequestWithRelevantMessage() throws Exception {
		IntegerRangeRequest request = IntegerRangeRequest.builder()
				.from(8)
				.to(7)
				.build();

		ErrorFieldsResponse expectedError = ErrorFieldsResponse.builder()
				.errorCount(1)
				.errorFields(Map.of("from", "value 8 cannot be higher than <TO> value 7"))
				.build();

		mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(expectedError)));
	}

	@Test()
	void testNonIntegerInInputReturnsBadRequest() throws Exception {
		String inputJson = """
        {
        	"from": 1,
        	"to": "a"
        }
        """;

		MvcResult result = mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(MediaType.APPLICATION_JSON)
						.content(inputJson))
				.andExpect(status().isBadRequest())
				.andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("JSON parse error"));
	}

	@Test
	void testMissingFieldReturnsBadRequest() throws Exception {
		IntegerRangeRequest request = IntegerRangeRequest.builder()
				.from(8)
				.build();

		ErrorFieldsResponse expectedError = ErrorFieldsResponse.builder()
				.errorCount(1)
				.errorFields(Map.of("to", "must not be null"))
				.build();

		mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(expectedError)));
	}

}
