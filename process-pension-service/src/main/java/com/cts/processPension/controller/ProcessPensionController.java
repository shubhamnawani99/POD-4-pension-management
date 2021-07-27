package com.cts.processPension.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.processPension.client.AuthorisationClient;
import com.cts.processPension.client.PensionDisbursementClient;
import com.cts.processPension.exception.InvalidTokenException;
import com.cts.processPension.exception.NotFoundException;
import com.cts.processPension.model.PensionDetail;
import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;
import com.cts.processPension.service.ProcessPensionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProcessPensionController {

	@Autowired
	PensionDisbursementClient pensionDisbursementClient;

	@Autowired
	ProcessPensionService processPensionService;

	@Autowired
	AuthorisationClient authorisationClient;

	/**
	 * @URL: http://localhost:8082/pensionerInput
	 * @Input: { "aadharNumber": "123456789012", "dateOfBirth": "1956-09-12",
	 *         "name": "Achyuth", "pan": "BHMER12436", "pensionType": "self" }
	 * @param pensionerInput
	 * @return
	 */
	@PostMapping("/pensionerInput")
	public ResponseEntity<PensionDetail> getPensionDetails(@RequestHeader(name = "Authorization") String token,
			@RequestBody @Valid PensionerInput pensionerInput) {
		log.info("START - getPensionDetails()");
		if (!authorisationClient.validate(token)) {
			throw new InvalidTokenException("You are not allowed to access this resource");
		}
		log.info("END - getPensionDetails()");
		return new ResponseEntity<>(processPensionService.getPensionDetails(pensionerInput), HttpStatus.OK);
	}

	/**
	 * @URL: http://localhost:8082/processPension
	 * @param token
	 * {"aadhaarNumber":"123456789012","pensionAmount":31600,"bankServiceCharge":550}
	 * @param processPensionInput
	 * @return status code indicating whether the process was success or not
	 */
	@PostMapping("/processPension")
	public ResponseEntity<ProcessPensionResponse> processPension(@RequestHeader(name = "Authorization") String token,
			@RequestBody ProcessPensionInput processPensionInput) {
		log.info("START - processPension()");

		if (!authorisationClient.validate(token)) {
			throw new InvalidTokenException("You are not allowed to access this resource");
		}
		log.info("END - processPension()");
		return new ResponseEntity<>(pensionDisbursementClient.disbursePension(token, processPensionInput), HttpStatus.OK);
	}

}