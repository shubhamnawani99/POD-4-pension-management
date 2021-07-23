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
@SpringBootTest
class PensionDetailsControllerTest {
	
	@InjectMocks
	PensionerDetailsController controller;

	@Mock
	PensionerdetailService service;
	
	@Autowired
	PensionerdetailService service2;

	/*
	 * @Before(value = "") public void init() { MockitoAnnotations.initMocks(this);
	 * }
	 */

	@Test
	public void testToGetCorrectPenionerDetailsFromController() throws Exception {
		PensionerDetails pensionerDetail = new PensionerDetails("Shubham", DateUtil.parseDate("29-01-1999"), "PCASD1234Q",
				27000, 10000, "self", new BankDetails("ICICI", 12345678, "private"));
		when(service.getPensionerDetailByAadhaarNumber(123456789012L)).thenReturn(pensionerDetail);
		PensionerDetails actual = controller.getPensionerDetailByAadhaar(123456789012L);
		assertNotNull(actual);
		assertEquals(actual, pensionerDetail);

	}

	@Test
	public void testForAadharNumberNotInCsvFile() throws NumberFormatException, IOException, NotFoundException, ParseException {

		PensionerDetails actual = service2.getPensionerDetailByAadhaarNumber(12345678888L);
		assertNull(actual);
	}

}
