package com.cts.disbursepension.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cts.disbursepension.model.PensionerDetail;

@FeignClient(name = "pension-detail-service", url = "http://localhost:8083")
public interface PensionerDetailsClient {
	/**
	 * method to get pensioner details by aadhaar number
	 * @param aadhaarNumber
	 * @return PensionerDetail
	 * 
	 */
	@GetMapping("/PensionerDetailByAadhaar")
	PensionerDetail pensionerDetailByAadhaar(String aadhaarNumber);

}
