package com.cts.pensionerDetails;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pensionerDetails.Exception.NotFoundException;
import com.cts.pensionerDetails.Model.BankDetails;
import com.cts.pensionerDetails.Model.PensionerDetails;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
class PensionerDetailsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void testBankDetails() {
		EqualsVerifier.simple().forClass(BankDetails.class).verify();
	}
	
	@Test
	void testPensionerDeatils() {
		EqualsVerifier.simple().forClass(PensionerDetails.class).verify();
	}
	
	@Test
	void testMainMethod() throws NumberFormatException, IOException, NotFoundException, ParseException {
		PensionerDetailsApplication.main(new String [] {});
	}


}
