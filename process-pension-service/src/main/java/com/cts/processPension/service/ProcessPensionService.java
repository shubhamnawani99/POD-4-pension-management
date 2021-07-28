package com.cts.processPension.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.processPension.exception.NotFoundException;
import com.cts.processPension.feign.PensionDisbursementClient;
import com.cts.processPension.feign.PensionerDetailsClient;
import com.cts.processPension.model.PensionAmountDetail;
import com.cts.processPension.model.PensionDetail;
import com.cts.processPension.model.PensionerDetail;
import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;
import com.cts.processPension.repository.PensionDetailsRepository;
import com.cts.processPension.repository.PensionerDetailsRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for Process Pension
 * 
 * @author Shubham Nawani
 *
 */
@Service
@Slf4j
public class ProcessPensionService {

	@Autowired
	private PensionerDetailsClient pensionerDetailClient;

	@Autowired
	private PensionDisbursementClient pensionDisbursementClient;

	@Autowired
	private PensionDetailsRepository pensionDetailsRepository;

	@Autowired
	private PensionerDetailsRepository pensionerDetailsRepository;

	/**
	 * This method is responsible to get the pension details if input details are
	 * valid
	 * 
	 * @author Shubham Nawani
	 * @param pensionerInput
	 * @return Verified Pension Detail with pension amount
	 */
	public PensionDetail getPensionDetails(PensionerInput pensionerInput) {

		// get the pensioner details from the detail micro-service
		PensionerDetail pensionerDetail = pensionerDetailClient
				.getPensionerDetailByAadhaar(pensionerInput.getAadhaarNumber());

		log.info("Details found by details microservice");

		// check if the entered details are correct
		if (checkdetails(pensionerInput, pensionerDetail)) {
			// save the input pensioner details into the database
			pensionerDetailsRepository.save(pensionerInput);
			// calculate the amount and return the pension detail object
			return calculatePensionAmount(pensionerDetail);
		} else {
			throw new NotFoundException("Details entered are incorrect");
		}
	}

	/**
	 * Calculate the pension amount and return the pensioner details according to
	 * the type of pension "self" or "family"
	 * 
	 * 
	 * @param Verified Pensioner Details
	 * @return Pension Details with Pension amount
	 */
	public PensionDetail calculatePensionAmount(PensionerDetail pensionDetail) {
		double pensionAmount = 0;
		if (pensionDetail.getPensionType().equalsIgnoreCase("self"))
			pensionAmount = (pensionDetail.getSalary() * 0.8 + pensionDetail.getAllowance());
		else if (pensionDetail.getPensionType().equalsIgnoreCase("family"))
			pensionAmount = (pensionDetail.getSalary() * 0.5 + pensionDetail.getAllowance());
		return new PensionDetail(pensionDetail.getName(), pensionDetail.getDateOfBirth(), pensionDetail.getPan(),
				pensionDetail.getPensionType(), pensionAmount);
	}

	/**
	 * Method to check the details entered by the user
	 * 
	 * @Data {"aadhaarNumber":"123456789012","pensionAmount":31600,"bankServiceCharge":550}
	 * @author Shubham Nawani
	 * @param PensionerInput
	 * @param PensionerDetail
	 * @return true if details match, else false
	 */
	public boolean checkdetails(PensionerInput pensionerInput, PensionerDetail pensionerDetail) {
		return (pensionerInput.getName().equalsIgnoreCase(pensionerDetail.getName())
				&& (pensionerInput.getDateOfBirth().compareTo(pensionerDetail.getDateOfBirth()) == 0)
				&& pensionerInput.getPan().equalsIgnoreCase(pensionerDetail.getPan())
				&& pensionerInput.getPensionType().equalsIgnoreCase(pensionerDetail.getPensionType()));
	}

	/**
	 * Method to get status code from the disbursement micro-service
	 * 
	 * @author Shubham Nawani
	 * @param token               Authentication JWT Token
	 * @param processPensionInput Processing input given by user
	 * @return status code: {10: Pension Disbursed, 21: Invalid Input}
	 */
	public ProcessPensionResponse processPension(String token, ProcessPensionInput processPensionInput) {
		int hitCounter = 0;
		ProcessPensionResponse pensionResponse = pensionDisbursementClient.disbursePension(token, processPensionInput);

		// retry the disbursement 2 more times if status code is 21
		while (pensionResponse.getProcessPensionStatusCode() == 21 && hitCounter < 2) {
			log.debug("Hitting the disbursement service again...");
			pensionResponse = pensionDisbursementClient.disbursePension(token, processPensionInput);
			++hitCounter;
		}

		// if response is 10, then we store the amount details in the database 
		pensionDetailsRepository.save(new PensionAmountDetail(
				processPensionInput.getAadhaarNumber(), 
				processPensionInput.getPensionAmount(),
				processPensionInput.getBankServiceCharge(),
				processPensionInput.getPensionAmount() - processPensionInput.getBankServiceCharge()));
		
		return pensionResponse;
	}
}