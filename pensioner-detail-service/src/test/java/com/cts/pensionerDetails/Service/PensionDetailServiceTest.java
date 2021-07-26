package com.cts.pensionerDetails.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;

//import org.junit.Rule;
import org.junit.jupiter.api.Test;
//import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pensionerDetails.Exception.NotFoundException;
import com.cts.pensionerDetails.Model.BankDetails;
import com.cts.pensionerDetails.Model.PensionerDetails;
import com.cts.pensionerDetails.Util.DateUtil;

/**
 * @author SREEKANTH GANTELA
 * 
 * This is the service test class
 * 
 * Test cases for the Pension Detail Service 
 *
 */
@SpringBootTest
class PensionDetailServiceTest {
	
	@InjectMocks
	private PensionerdetailService pds;
	
	@Autowired
	PensionerdetailService service;
	
	/**
	 * Test the Pensioner Detail service is null or not
	 */

	@Test
	public void testNotNullPensionDetailServiceObject() {
		assertNotNull(pds);
	}

	/**
	 * Test Case for the Correct Details Returned From Service With Correct AadharNumber
	 */

	@Test
	public void testCorrectDetailsReturnedFromServiceWithCorrectAadharNumber() throws IOException, NotFoundException,
			NumberFormatException, com.cts.pensionerDetails.Exception.NotFoundException, ParseException, NullPointerException {

		PensionerDetails pensionerDetail = new PensionerDetails("Achyuth", DateUtil.parseDate("12-09-1956"), "BHMER12436",
				27000, 10000, "self", new BankDetails("ICICI", 12345678, "private"));
		assertEquals(pds.getPensionerDetailByAadhaarNumber(123456789012L), pensionerDetail);
	}
	/**
	 * Test Case for the InCorrect Details Returned From Service With Correct AadharNumber
	 */
	
	@Test
	public void testForIncorrectAadharNumber()
			throws NumberFormatException, IOException, NotFoundException, ParseException {
		NotFoundException exception= assertThrows(NotFoundException.class, () -> {pds.getPensionerDetailByAadhaarNumber(12345678);});
		assertTrue(exception.getMessage().contains("AadharNumber Not Found"));
		
		
	}

}
