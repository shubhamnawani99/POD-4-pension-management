package com.cts.disbursepension.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.disbursepension.model.ProcessPensionInput;
import com.cts.disbursepension.model.ProcessPensionResponse;
import com.cts.disbursepension.service.IDisbursePensionService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * DisbursePension Controller which handles request to verify pension amount and
 * bank charges
 * 
 * @author Anas Zubair
 *
 */

@RestController
@CrossOrigin
@Slf4j
public class DisbursePensionController {
	@Autowired
	IDisbursePensionService disbursePensionService;

	/**
	 * @URL: http://localhost:8084/healthCheck
	 * @return "OK" when server and controller is up and running
	 */
	@GetMapping("/HealthCheck")
	@ApiOperation(value = "healthCheck", notes = "Check whether microservice is up and running or not", httpMethod = "GET", response = String.class)
	public ResponseEntity<String> healthCheck() {
		log.info("Health-Check: OK");
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	/**
	 * @URL: http://localhost:8084/DisbursePension
	 * @param ProcessPensionInput {"aadhaarNumber":"300545673456","pensionAmount":34563,"bankServiceCharge":500}
	 * @return ProcessPensionResponse with processPensionStatusCode 10 if
	 *         processPensionInput is correct otherwise 21
	 */
	@PostMapping("/DisbursePension")
	@ApiOperation(value = "disbursePension", notes = "Verify pension amount and bank service charge for given aadhaar", httpMethod = "POST", response = ProcessPensionResponse.class)
	public ResponseEntity<ProcessPensionResponse> disbursePension(
			@RequestBody ProcessPensionInput processPensionInput) {
		return new ResponseEntity<>(disbursePensionService.verifyPension(processPensionInput), HttpStatus.OK);
	}

}
