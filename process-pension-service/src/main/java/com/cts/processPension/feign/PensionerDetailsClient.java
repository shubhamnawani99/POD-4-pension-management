package com.cts.processPension.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.processPension.model.PensionerDetail;

/**
 * Feign client to connect with Pension details micro-service
 * 
 * @author Shubham Nawani
 *
 */
@FeignClient("PENSIONER-DETAIL-SERVICE")
public interface PensionerDetailsClient {
	@GetMapping("/pensionerDetailByAadhaar/{aadhaarNumber}")
	public PensionerDetail getPensionerDetailByAadhaar(@PathVariable String aadhaarNumber);
}