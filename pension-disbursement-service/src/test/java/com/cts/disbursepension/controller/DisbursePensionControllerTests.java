package com.cts.disbursepension.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.cts.disbursepension.feign.AuthorisationClient;
import com.cts.disbursepension.feign.PensionerDetailsClient;
import com.cts.disbursepension.model.ProcessPensionInput;
import com.cts.disbursepension.model.ProcessPensionResponse;
import com.cts.disbursepension.service.IDisbursePensionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import feign.Request;
import feign.Request.HttpMethod;

/**
 * Test cases for DisbursePension Controller
 * 
 * @author Anas Zubair
 *
 */

@WebMvcTest(DisbursePensionController.class)
class DisbursePensionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IDisbursePensionService disbursePensionService;

	@MockBean
	private AuthorisationClient authorisationClient;

	@MockBean
	private PensionerDetailsClient pensionerDetailsClient;

	@Autowired
	private ObjectMapper objectMapper;

	private ProcessPensionInput validProcessPensionInput;
	private ProcessPensionInput invalidProcessPensionInput;

	@BeforeEach
	void setup() {

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

	}

	@Test
	@DisplayName("testing /heathCheck endpoint")
	void testHealthCheck() throws Exception {
		mockMvc.perform(get("/HealthCheck")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Verify response after sending post request with valid data to /DisbursePension")
	void testDisbursePension_withValidInput() throws Exception {

		// mock disbursePensionSerive response
		when(disbursePensionService.verifyPension(ArgumentMatchers.any())).thenReturn(new ProcessPensionResponse(10));

		// mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(true);

		// performing test
		mockMvc.perform(post("/DisbursePension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
				.content(objectMapper.writeValueAsString(validProcessPensionInput)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.processPensionStatusCode", Matchers.equalTo(10)));
	}

	@Test
	@DisplayName("Verify response after sending post request with invalid data to /DisbursePension")
	void testDisbursePension_withInvalidInput() throws Exception {

		// mock disbursePensionSerive response
		when(disbursePensionService.verifyPension(ArgumentMatchers.any())).thenReturn(new ProcessPensionResponse(21));

		// mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(true);

		// performing test
		mockMvc.perform(post("/DisbursePension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(invalidProcessPensionInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).andExpect(status().isOk())
				.andExpect(jsonPath("$.processPensionStatusCode", Matchers.equalTo(21)));
	}

	@Test
	@DisplayName("Verify response after sending post request with invalid token to /DisbursePension")
	void testDisbursePension_withInvalidToken() throws Exception {

		// mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(false);

		//performing test
		mockMvc.perform(post("/DisbursePension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(invalidProcessPensionInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "user1")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Verify response for Invalid Aadhar Number")
	void testDisbursePension_withInvalidAadhaar() throws JsonProcessingException, Exception  {
		
		// mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(true);
		
		//mock disbursePensionService verifyPension to throw FeignException
		when(disbursePensionService.verifyPension(ArgumentMatchers.any()))
				.thenThrow(new FeignException.BadRequest("Aadhaar number not found",
						Request.create(HttpMethod.GET, "", Collections.emptyMap(), null, null, null), null));

		// performing test
		mockMvc.perform(post("/DisbursePension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(invalidProcessPensionInput)).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).andExpect(status().isBadRequest());
		
	}

	@Test
	@DisplayName("Verify response for Invalid Arguments")
	void testDisbursePension_withInvalidArgument() throws Exception {
		//mock authorization microservice response
		when(authorisationClient.validate(ArgumentMatchers.anyString())).thenReturn(true);

		//performing test
		mockMvc.perform(post("/DisbursePension").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content("{\"body\":\"invalid\"}").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")).andExpect(status().isBadRequest());
	}

}
