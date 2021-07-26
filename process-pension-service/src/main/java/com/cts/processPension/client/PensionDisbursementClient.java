package com.cts.processPension.client;

import java.io.IOException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.processPension.exception.NotFoundException;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;

@FeignClient(name = "PensionDisbursementService", url = "http://localhost:8082/")
public interface PensionDisbursementClient {
	@PostMapping("/disbursePension")
	public ProcessPensionResponse getcode(@RequestBody ProcessPensionInput processPensionInput)
			throws IOException, NotFoundException;
}