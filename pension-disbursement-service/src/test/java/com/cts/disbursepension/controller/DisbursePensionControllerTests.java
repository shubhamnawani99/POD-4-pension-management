package com.cts.disbursepension.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

import com.cts.disbursepension.model.ProcessPensionInput;
import com.cts.disbursepension.model.ProcessPensionResponse;
import com.cts.disbursepension.service.IDisbursePensionService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test cases for DisbursePension Controller
 * @author Anas Zubair
 *
 */

@WebMvcTest(DisbursePensionController.class)
class DisbursePensionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IDisbursePensionService disbursePensionService;

	@Autowired
	private ObjectMapper objectMapper;

	private ProcessPensionInput validProcessPensionInput;
	private ProcessPensionInput invalidProcessPensionInput;

	@BeforeEach
	void generatePensionerDetails() {
		
		validProcessPensionInput = ProcessPensionInput.builder().aadhaarNumber("300546467895").pensionAmount(138752.0)
				.bankServiceCharge(500).build();

		invalidProcessPensionInput = ProcessPensionInput.builder().aadhaarNumber("300546467895").pensionAmount(145000)
				.bankServiceCharge(550).build();
	}

	@Test
	@DisplayName("Verify response after sending post request with valid data to /DisbursePension")
	void testDisbursePension_withValidInput() throws Exception {
		
		//mock disbursePensionSerive response
		when(disbursePensionService.verifyPension(ArgumentMatchers.any())).thenReturn(ProcessPensionResponse.builder().processPensionStatusCode(10).build());
		
		//performing test
		mockMvc.perform(post("/DisbursePension")
				.contentType(MediaType.APPLICATION_JSON)
			    .characterEncoding("utf-8")
			    .content(objectMapper.writeValueAsString(validProcessPensionInput))
			    .accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.processPensionStatusCode",Matchers.equalTo(10)));
	}
	
	@Test
	@DisplayName("Verify response after sending post request with invalid data to /DisbursePension")
	void testDisbursePension_withInvalidInput() throws Exception {
		
		//mock disbursePensionSerive response
		when(disbursePensionService.verifyPension(ArgumentMatchers.any())).thenReturn(ProcessPensionResponse.builder().processPensionStatusCode(21).build());
		
		//performing test
		mockMvc.perform(post("/DisbursePension")
				.contentType(MediaType.APPLICATION_JSON)
			    .characterEncoding("utf-8")
			    .content(objectMapper.writeValueAsString(invalidProcessPensionInput))
			    .accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.processPensionStatusCode",Matchers.equalTo(21)));
	}

}
