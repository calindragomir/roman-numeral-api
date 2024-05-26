package org.project.romannumeral;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.openapi.dto.ErrorMessageResponse;
import org.project.openapi.dto.IntegerRangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RomanNumeralApplicationTests {
	private static final String APP_MAIN_ROUTE = "/api/v1/convert/integers";
	private static final String ERROR_MSG_FORMAT = "Field `%s` has an error: %s";

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void testNormalCaseWorks() throws Exception {
		IntegerRangeRequest request = new IntegerRangeRequest()
				.from(1)
				.to(5);

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
		IntegerRangeRequest request = new IntegerRangeRequest()
				.from(1)
				.to(3999);

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
		IntegerRangeRequest request = new IntegerRangeRequest()
				.from(-1)
				.to(4000);

		ErrorMessageResponse expectedError = new ErrorMessageResponse()
				.errorCount(2)
				.errors(List.of(
						ERROR_MSG_FORMAT.formatted(
								"from",
								"must be greater than or equal to 1"),
						ERROR_MSG_FORMAT.formatted(
								"to",
								"must be less than or equal to 3999")
						)
				);

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
		IntegerRangeRequest request = new IntegerRangeRequest()
				.from(8)
				.to(7);

		ErrorMessageResponse expectedError = new ErrorMessageResponse()
				.errorCount(1)
				.errors(List.of(
						ERROR_MSG_FORMAT.formatted(
								"from",
								"value 8 cannot be higher than <TO> value 7")
						)
				);

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
		IntegerRangeRequest request = new IntegerRangeRequest()
				.from(8);

		ErrorMessageResponse expectedError = new ErrorMessageResponse()
				.errorCount(1)
				.errors(List.of(
						ERROR_MSG_FORMAT.formatted(
								"to",
								"must not be null")
						)
				);

		mockMvc.perform(post(APP_MAIN_ROUTE)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(expectedError)));
	}

}
