package com.cts.processPension.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.processPension.model.PensionerDetail;

@FeignClient(name = "pensioner-details-service", url = "http://localhost:8083/")
public interface PensionerDetailClient {
	@GetMapping("/pensionerDetailByAadhaar/{aadhaarNumber}")
	public PensionerDetail getPensionerDetailByAadhaar(@PathVariable String aadhaarNumber);
}