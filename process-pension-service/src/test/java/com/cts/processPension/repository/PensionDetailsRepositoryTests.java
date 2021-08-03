package com.cts.processPension.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.processPension.model.PensionAmountDetail;
import com.cts.processPension.model.PensionerDetail;
import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Test class to test all the repository functionality
 * 
 * @author Shubham Nawani
 *
 */
@SpringBootTest
@Slf4j
class PensionDetailsRepositoryTests {

	@Autowired
	private PensionDetailsRepository pensionDetailsRepository;

	@Autowired
	private PensionerDetailsRepository pensionerDetailsRepository;

	@Test
	@DisplayName("This method is responsible to test save()")
	void testSave() {
		log.info("START - testSave()");

		PensionAmountDetail pensionAmountDetail = new PensionAmountDetail();
		pensionAmountDetail.setAadhaarNumber("123456789012");
		pensionAmountDetail.setBankServiceCharge(550.00);
		pensionAmountDetail.setPensionAmount(31600.00);
		pensionAmountDetail.setFinalAmount(31050.00);

		PensionAmountDetail savedDetails = pensionDetailsRepository.save(pensionAmountDetail);
		assertEquals(savedDetails.getAadhaarNumber(), pensionAmountDetail.getAadhaarNumber());
		assertEquals(savedDetails.getBankServiceCharge(), pensionAmountDetail.getBankServiceCharge());
		assertEquals(savedDetails.getPensionAmount(), pensionAmountDetail.getPensionAmount());
		assertEquals(savedDetails.getFinalAmount(), pensionAmountDetail.getFinalAmount());

		assertNotNull(savedDetails);
		log.info("END - testSave()");
	}

	@Test
	@DisplayName("This method is responsible to test save() for pensioner details")
	void testSaveForPensionerDetails() throws ParseException {
		log.info("START - testSaveForPensionerDetails()");

		PensionerInput pi_empty = new PensionerInput();
		PensionerInput pi = new PensionerInput("Shubham", DateUtil.parseDate("1999-02-02"), "BHPKN12931",
				"211228329912", "Self");

		PensionerInput savedDetails = pensionerDetailsRepository.save(pi);
		assertEquals(savedDetails.getAadhaarNumber(), pi.getAadhaarNumber());
		assertNotNull(savedDetails);
		
		log.info("END - testSaveForPensionerDetails()");
	}
}
