package com.cts.disbursepension.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.disbursepension.feign.PensionerDetailsClient;
import com.cts.disbursepension.model.BankDetails;
import com.cts.disbursepension.model.PensionerDetail;
import com.cts.disbursepension.model.ProcessPensionInput;

/**
 * Test Cases for Disburse Pension Service
 * @author Anas Zubair
 *
 */
@SpringBootTest
class DisbursePensionServiceTests {

	@Autowired
	private IDisbursePensionService disbursePensionService;

	@MockBean
	private PensionerDetailsClient pensionerDetailsClient;

	private PensionerDetail pensionerDetail;

	@BeforeEach()
	void generatePensionerDetails() {
		
		pensionerDetail = new PensionerDetail();
		pensionerDetail.setName("Jon Lanister");
		pensionerDetail.setAllowance(15000.8);
		pensionerDetail.setBank(new BankDetails("HDFC", 101, "public"));
		pensionerDetail.setDateOfBirth(new Date(System.currentTimeMillis()));
		pensionerDetail.setPan("AZTYK3456");
		pensionerDetail.setPensionType("self");
		pensionerDetail.setSalary(154689.0);
	}

	@Test
	@DisplayName("Testing findBankCharges method of Disburse Pension Service")
	void testFindBankCharges() {

		// testing output of findBankCharges for public and private bank type
		assertEquals(500, disbursePensionService.findBankCharges("public"));
		assertEquals(550, disbursePensionService.findBankCharges("private"));
	}

	@Test
	@DisplayName("Testing verifyPensionDetails method of Disburse Pension Service")
	void testGetPensionDetails() {

		// mock pensioner detail microservice response
		when(pensionerDetailsClient.pensionerDetailByAadhaar(ArgumentMatchers.anyString())).thenReturn(pensionerDetail);

		// testing getPensionDetail output for given aadhaarNumber
		PensionerDetail receivedPensionerDetails = disbursePensionService.getPensionerDetail("456178956325"); 
		assertEquals(101, receivedPensionerDetails.getBank().getAccountNumber());
		assertEquals("HDFC", receivedPensionerDetails.getBank().getBankName());
		assertEquals("Jon Lanister", receivedPensionerDetails.getName());
		assertTrue(receivedPensionerDetails.getDateOfBirth().before(new Date(System.currentTimeMillis())));
		assertEquals("AZTYK3456", receivedPensionerDetails.getPan());

	}

	@Test
	@DisplayName("Testing verifyPensionAmount method of Disburse Pension Service")
	void testVerifyPensionAmount() {

		// testing verify pension amount
		//having self pensionType
		assertTrue(disbursePensionService.verifyPensionAmount(pensionerDetail, 138752.0));
		assertFalse(disbursePensionService.verifyPensionAmount(pensionerDetail, 134000.0));
		
		//having family pensionType
		pensionerDetail.setPensionType("family");
		assertTrue(disbursePensionService.verifyPensionAmount(pensionerDetail, 92345.3));
		assertFalse(disbursePensionService.verifyPensionAmount(pensionerDetail, 1023546.0));

	}

	@Test
	@DisplayName("Testing verifyBankCharges method of Disburse Pension Service")
	void testVerifyBankCharges() {

		// testing verifyBankCharges for public bank
		assertTrue(disbursePensionService.verifyBankCharges("public", 500));
		assertFalse(disbursePensionService.verifyBankCharges("public", 510));

		// testing verfifyBankCharges for private bank
		assertTrue(disbursePensionService.verifyBankCharges("private", 550));
		assertFalse(disbursePensionService.verifyBankCharges("private", 500));
	}

	@Test
	@DisplayName("Testing verifyPension method of Disburse Pension Service")
	void testVerifyPension() {
		ProcessPensionInput processPensionInput = new ProcessPensionInput("456178956325",138752,500);
		// mock pensioner detail microservice response
		when(pensionerDetailsClient.pensionerDetailByAadhaar(ArgumentMatchers.anyString())).thenReturn(pensionerDetail);

		// testing valid pension details
		assertEquals(10, disbursePensionService.verifyPension(processPensionInput).getProcessPensionStatusCode());

		// testing for invalid pension details
		processPensionInput.setBankServiceCharge(550);
		assertEquals(21, disbursePensionService.verifyPension(processPensionInput).getProcessPensionStatusCode());
		processPensionInput.setPensionAmount(1234567);
		assertEquals(21, disbursePensionService.verifyPension(processPensionInput).getProcessPensionStatusCode());

		processPensionInput.setBankServiceCharge(500);
		assertEquals(21, disbursePensionService.verifyPension(processPensionInput).getProcessPensionStatusCode());

	}

}
