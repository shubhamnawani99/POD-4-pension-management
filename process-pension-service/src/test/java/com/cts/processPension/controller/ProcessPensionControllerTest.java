package com.cts.processPension.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.processPension.exception.ErrorResponse;
import com.cts.processPension.exception.NotFoundException;
import com.cts.processPension.feign.AuthorisationClient;
import com.cts.processPension.feign.PensionDisbursementClient;
import com.cts.processPension.feign.PensionerDetailsClient;
import com.cts.processPension.model.PensionDetail;
import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;
import com.cts.processPension.service.ProcessPensionServiceImpl;
import com.cts.processPension.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import feign.Request;
import feign.Request.HttpMethod;

/**
 * Test cases for Process pension Controller
 * 
 * @author Shubham Nawani
 *
 */

@WebMvcTest(ProcessPensionController.class)
class ProcessPensionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorisationClient authorisationClient;

	@MockBean
	private PensionerDetailsClient pensionerDetailsClient;

	@MockBean
	private PensionDisbursementClient pensionDisbursementClient;

	@MockBean
	private ProcessPensionServiceImpl processPensionService;

	@Autowired
	private ObjectMapper objectMapper;

	private ProcessPensionInput validProcessPensionInput;
	private ProcessPensionInput invalidProcessPensionInput;
	private PensionerInput validPensionerInput;
	private PensionerInput invalidPensionerInput;
	private PensionDetail pensionDetail;

	// setup for process-pension input
	@BeforeEach
	void setup() throws ParseException {

		// valid ProcessPensionInput
		validProcessPensionInput = new ProcessPensionInput();
		validProcessPensionInput.setAadhaarNumber("300546467895");
		validProcessPensionInput.setPensionAmount(138752.0);
		validProcessPensionInput.setBankServiceCharge(500);

		// invalid ProcessPensionInput
		invalidProcessPensionInput = new ProcessPensionInput();
		invalidProcessPensionInput.setAadhaarNumber("300546467895");
		invalidProcessPensionInput.setPensionAmount(145000.0);
		invalidProcessPensionInput.setBankServiceCharge(550);

		// valid PensionerInput
		validPensionerInput = new PensionerInput("Shubham", DateUtil.parseDate("16-02-1999"), "ABCDE12345",
				"300546467895", "self");

		// invalid PensionerInput
		invalidPensionerInput = new PensionerInput("Shubham", DateUtil.parseDate("16-02-1999"), "ABCDE12345", "",
				"self");

		// correct PensionDetails
		pensionDetail = new PensionDetail("Shubham", DateUtil.parseDate("16-02-1999"), "ABCDE12345", "family", 50000);

		// mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(true);

	}

	@Test
	@DisplayName("Verify response after sending post request with valid data to /processPension")
	void testProcessPension_withValidInput() throws Exception {

		// mock disbursePensionSerive response
		when(processPensionService.processPension(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(new ProcessPensionResponse(10));

		// performing test
		mockMvc.perform(post("/processPension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
				.content(objectMapper.writeValueAsString(validProcessPensionInput)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.processPensionStatusCode", Matchers.equalTo(10)));
	}

	@Test
	@DisplayName("Verify response after sending post request with invalid data to /processPension")
	void testProcessPension_withInvalidInput() throws Exception {

		ProcessPensionResponse processPensionResponse = new ProcessPensionResponse();
		processPensionResponse.setProcessPensionStatusCode(21);
		
		// mock disbursePensionSerive response
		when(processPensionService.processPension(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(processPensionResponse);

		// performing test
		mockMvc.perform(post("/processPension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
				.content(objectMapper.writeValueAsString(validProcessPensionInput)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.processPensionStatusCode", Matchers.equalTo(21)));
	}

	@Test
	@DisplayName("Verify response after sending post request with invalid token to /processPension")
	void testDisbursePension_withInvalidToken() throws Exception {

		// mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(false);

		// performing test
		mockMvc.perform(post("/processPension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(invalidProcessPensionInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "user1")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Verify response for Invalid Arguments")
	void testDisbursePension_withInvalidArgument() throws Exception {

		// performing test
		mockMvc.perform(post("/processPension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content("invalid").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).andExpect(status().isBadRequest());
	}

	/***************************************
	 * 
	 * Test cases for GetPensionDetails()
	 * 
	 ***************************************
	 */
	@Test
	@DisplayName("Verify response after sending post request with valid data to /pensionerInput")
	void testGetPensionDetails_withValidInput() throws Exception {

		// mock disbursePensionSerive response
		when(processPensionService.getPensionDetails(ArgumentMatchers.any())).thenReturn(pensionDetail);

		// performing test
		mockMvc.perform(post("/pensionerInput").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
				.content(objectMapper.writeValueAsString(validPensionerInput)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.pensionAmount", Matchers.equalTo(50000.0)));
	}

	@Test
	@DisplayName("Verify response after sending post request with invalid token to /pensionerInput")
	void testGetPensionDetails_withInvalidToken() throws Exception {
		
		// mock authorization microservice response for invalid token
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(false);

		// performing test
		mockMvc.perform(post("/pensionerInput").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(validPensionerInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "user1")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("This method is responsible to test Global Handler")
	void testGlobalExceptions() throws Exception {

		final String errorMessage = "Invalid Credentials";

		// performing test
		mockMvc.perform(post("/pensionerInput").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
				.content(objectMapper.writeValueAsString(invalidPensionerInput)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message", Matchers.equalTo(errorMessage)));
	}

	@Test
	@DisplayName("Verify response after sending post request with invalid data to /pensionerInput")
	void testPensionInput_withInvalidInput() throws Exception {

		// mock processPensionService response
		when(processPensionService.getPensionDetails(ArgumentMatchers.any()))
				.thenThrow(new NotFoundException("Details entered are incorrect"));

		// performing test
		mockMvc.perform(post("/pensionerInput").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
				.content(objectMapper.writeValueAsString(validPensionerInput)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message", Matchers.equalTo("Details entered are incorrect")));
	}

	/**
	 * Test cases for invalid inputs and global handler
	 */
	@Test
	@DisplayName("Verify Response when feign client returns valid error response")
	void testDisbursePension_withValidFeignResponse() throws JsonProcessingException, Exception {
		// mock processPensionService getPensionDetails to throw FeignException
		when(processPensionService.getPensionDetails(ArgumentMatchers.any())).thenThrow(new FeignException.BadRequest(
				"Service is offline", Request.create(HttpMethod.GET, "", Collections.emptyMap(), null, null, null),
				objectMapper.writeValueAsBytes(new ErrorResponse("Internal Server Error"))));

		// performing test
		mockMvc.perform(post("/pensionerInput").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(validPensionerInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", Matchers.equalTo("Internal Server Error")));

	}
	
	@Test
	@DisplayName("Verify Response when feign client returns invalid error response")
	void testPensionInput_withInvalidFeignResponse() throws JsonProcessingException, Exception {
		// mock processPensionService getPensionDetails to throw FeignException
		when(processPensionService.getPensionDetails(ArgumentMatchers.any())).thenThrow(new FeignException.BadRequest(
				"Invalid Response", Request.create(HttpMethod.GET, "", Collections.emptyMap(), null, null, null),
				"Unknown error response".getBytes()));

		// performing test
		mockMvc.perform(post("/pensionerInput").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(validPensionerInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", Matchers.equalTo("Unknown error response")));
	}
	
	@Test
	@DisplayName("Verify Response when feign client returns empty message response")
	void testPensionInput_withEmptyFeignResponse() throws JsonProcessingException, Exception {
		// mock processPensionService getPensionDetails to throw FeignException
		when(processPensionService.getPensionDetails(ArgumentMatchers.any())).thenThrow(new FeignException.BadRequest(
				"Invalid Response", Request.create(HttpMethod.GET, "", Collections.emptyMap(), null, null, null),
				"".getBytes()));

		// performing test
		mockMvc.perform(post("/pensionerInput").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(validPensionerInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", Matchers.equalTo("Invalid Request")));
	}
}
