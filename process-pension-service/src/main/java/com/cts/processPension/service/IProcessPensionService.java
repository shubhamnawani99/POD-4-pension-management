package com.cts.processPension.service;

import com.cts.processPension.model.PensionDetail;
import com.cts.processPension.model.PensionerDetail;
import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;

/**
 * Implementaion class for Process Pension
 * 
 * @author Shubham Nawani
 *
 */
public interface IProcessPensionService {

	/**
	 * This method is responsible to get the pension details if input details are
	 * valid
	 * 
	 * @param pensionerInput
	 * @return Verified Pension Detail with pension amount
	 */
	public PensionDetail getPensionDetails(PensionerInput pensionerInput);

	/**
	 * Calculate the pension amount and return the pensioner details according to
	 * the type of pension "self" or "family"
	 * 
	 * @param Verified Pensioner Details
	 * @return Pension Details with Pension amount
	 */
	public PensionDetail calculatePensionAmount(PensionerDetail pensionDetail);

	/**
	 * Method to check the details entered by the user
	 * 
	 * @param PensionerInput
	 * @param PensionerDetail
	 * @return true if details match, else false
	 */
	public boolean checkdetails(PensionerInput pensionerInput, PensionerDetail pensionerDetail);
	
	/**
	 * Method to get status code from the disbursement micro-service
	 * 
	 * @param token               Authentication JWT Token
	 * @param processPensionInput Processing input given by user
	 * @return status code: {10: Pension Disbursed, 21: Invalid Input}
	 */
	public ProcessPensionResponse processPension(String token, ProcessPensionInput processPensionInput);
}
