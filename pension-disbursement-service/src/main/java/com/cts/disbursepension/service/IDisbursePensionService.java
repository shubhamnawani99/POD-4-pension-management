package com.cts.disbursepension.service;

import com.cts.disbursepension.model.PensionerDetail;
import com.cts.disbursepension.model.ProcessPensionInput;
import com.cts.disbursepension.model.ProcessPensionResponse;

public interface IDisbursePensionService {

	/**
	 * find bank charges for a particular bank type in local repository
	 * 
	 * @param bankType {public,private}
	 * @return Integer
	 * 
	 */
	public double findBankCharges(String bankType);

	/**
	 * verify pension amount and bank charges in ProcessPensionInput
	 * 
	 * @param processPensionInput
	 * @return ProcessPensionResponse with processPensionStatusCode 10 if all
	 *         details are valid otherwise 21
	 */
	public ProcessPensionResponse verifyPension(ProcessPensionInput processPensionInput);

	/**
	 * verify pension amount by calculation pension amount using details in pensionerDetail
	 * @param pensionerDetail
	 * @param pensionAmount
	 * @return true if pensionAmount is correct otherwise false
	 */
	public boolean verifyPensionAmount(PensionerDetail pensionerDetail, double pensionAmount);

	/**
	 * verify bankCharges for given bankType
	 * @param bankType
	 * @param bankCharges
	 * @return true if bankCharges is valid for given bankType else false
	 */
	public boolean verifyBankCharges(String bankType, double bankCharges);

	/**
	 * get pensioner detail from pensioner-detail-microservice using given aadhaarNumber
	 * @param aadhaarNumber
	 * @return PensionerDetail response that is received from pensioner-detail-microservice 
	 */
	public PensionerDetail getPensionerDetail(String aadhaarNumber);

}
