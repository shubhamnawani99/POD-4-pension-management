package com.cts.pensionerDetails.Service;

import com.cts.pensionerDetails.Model.PensionerDetails;

/**
 * Interface providing implementations for PensionerDetailService
 * 
 */
public interface IPensionerDetailService {

	public PensionerDetails getPensionerDetailByAadhaarNumber(String aadhaarNumber);

}
