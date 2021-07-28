package com.cts.processPension.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.processPension.client.PensionerDetailClient;
import com.cts.processPension.exception.NotFoundException;
import com.cts.processPension.model.PensionDetail;
import com.cts.processPension.model.PensionerDetail;
import com.cts.processPension.model.PensionerInput;

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
	PensionerDetailClient pensionerDetailClient;

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
	 * @Data: {"aadhaarNumber":"123456789012","pensionAmount":31600,"bankServiceCharge":550}
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
}