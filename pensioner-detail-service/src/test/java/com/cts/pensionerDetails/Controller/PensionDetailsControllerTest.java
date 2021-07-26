package com.cts.pensionerDetails.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;

//import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
//import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pensionerDetails.Exception.NotFoundException;
import com.cts.pensionerDetails.Model.BankDetails;
import com.cts.pensionerDetails.Model.PensionerDetails;
import com.cts.pensionerDetails.Service.PensionerdetailService;
import com.cts.pensionerDetails.Util.DateUtil;
/**
 * @author SREEKANTH GANTELA
 * Test cases for the pensioner Details controller
 *
 */
@SpringBootTest
class PensionDetailsControllerTest {
	
	@InjectMocks
	PensionerDetailsController controller;

	@Mock
	PensionerdetailService service;
	
	@Autowired
	PensionerdetailService service2;

	/**
	 * Test Case for test To Get Correct Pensioner Details From Controller
	 * @throws Exception
	 */
	@Test
	public void testToGetCorrectPensionerDetailsFromController() throws Exception {
		PensionerDetails pensionerDetail = new PensionerDetails("Shubham", DateUtil.parseDate("29-01-1999"), "PCASD1234Q",
				27000, 10000, "self", new BankDetails("ICICI", 12345678, "private"));
		when(service.getPensionerDetailByAadhaarNumber(123456789012L)).thenReturn(pensionerDetail);
		PensionerDetails actual = controller.getPensionerDetailByAadhaar(123456789012L);
		assertNotNull(actual);
		assertEquals(actual, pensionerDetail);

	}

	/**
	 * 
	 *Test Case for the Aadhaar Number Not In Csv File
	 */
	@Test
	public void testForAadharNumberNotInCsvFile() throws NumberFormatException, IOException, NotFoundException, ParseException {

		//PensionerDetails actual = service2.getPensionerDetailByAadhaarNumber(12345678888L);
		NotFoundException exception= assertThrows(NotFoundException.class, () -> {service2.getPensionerDetailByAadhaarNumber(12345678888L);});
		assertTrue(exception.getMessage().contains("AadharNumber Not Found"));
	}

}
