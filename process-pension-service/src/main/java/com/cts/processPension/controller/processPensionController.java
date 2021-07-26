package com.cts.processPension.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.processPension.exception.NotFoundException;
import com.cts.processPension.model.PensionDetail;
import com.cts.processPension.model.PensionerDetail;
import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;
import com.cts.processPension.service.ProcessPensionService;
import com.cts.processPension.client.PensionDisbursementClient;
import com.cts.processPension.client.PensionerDetailClient;

@RestController
public class processPensionController {

	@Autowired
	PensionerDetailClient pensionerDetailClient;
	@Autowired
	PensionDisbursementClient pensionDisbursementClient;
	@Autowired
	ProcessPensionService processPensionService;

	@PostMapping("/pensionerInput")
	public PensionDetail getPensionDetails(@RequestBody PensionerInput pensionerInput) {

		System.out.println(pensionerInput);

		PensionerDetail pensionerDetail = pensionerDetailClient
				.getPensionerDetailByAadhaar(pensionerInput.getAadharNumber());

		System.out.println(pensionerDetail);
		
		PensionDetail pensionDetail = null;
		
		if(pensionerDetail == null) {
			return pensionDetail;
		}
		
		ProcessPensionResponse ppr = processPensionService.checkdetails(pensionerInput, pensionerDetail);

		if (ppr.getPensionStatusCode() == 10) {
			pensionDetail = processPensionService.getresult(pensionerDetail);

			ProcessPensionInput processPensionInput = new ProcessPensionInput(pensionerInput.getAadharNumber(),
					(Double) pensionDetail.getPensionAmount(), 500);

			System.out.println(processPensionInput);

			try {
				ppr = this.getcode(processPensionInput);
				if (ppr.getPensionStatusCode() == 21) {
					pensionDetail.setPensionAmount(pensionDetail.getPensionAmount() - 550);
				} else if (ppr.getPensionStatusCode() == 10) {
					pensionDetail.setPensionAmount(pensionDetail.getPensionAmount() - 500);
				}
			} catch (IOException | NotFoundException e) {
				return null;
			}
		}
		// Pension Details
		System.out.println(pensionDetail);
		return pensionDetail;
	}

	@PostMapping("/ProcessPension")
	public ProcessPensionResponse getcode(@RequestBody ProcessPensionInput processPensionInput)
			throws IOException, NotFoundException {
		return pensionDisbursementClient.getcode(processPensionInput);
	}

}