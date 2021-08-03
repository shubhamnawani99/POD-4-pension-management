package com.cts.processPension.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.processPension.exception.InvalidTokenException;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;

/**
 * Feign client to connect with Pension disbursement micro-service
 * 
 * @author Shubham Nawani
 *
 */
@FeignClient("PENSION-DISBURSEMENT-SERVICE")
public interface PensionDisbursementClient {
	@PostMapping("/DisbursePension")
	public ProcessPensionResponse disbursePension(@RequestHeader(name = "Authorization") String token,
			@RequestBody ProcessPensionInput processPensionInput) throws InvalidTokenException;
}