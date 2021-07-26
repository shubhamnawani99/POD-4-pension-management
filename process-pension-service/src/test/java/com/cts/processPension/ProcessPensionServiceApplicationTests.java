package com.cts.processPension;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.processPension.exception.NotFoundException;
import com.cts.processPension.model.Bank;
import com.cts.processPension.model.PensionDetail;
import com.cts.processPension.model.PensionerDetail;
import com.cts.processPension.model.PensionerInput;
import com.cts.processPension.model.ProcessPensionInput;
import com.cts.processPension.model.ProcessPensionResponse;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
class ProcessPensionApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testBankDetails() {
		EqualsVerifier.simple().forClass(Bank.class).verify();
	}

	@Test
	void testPensionerDeatils() {
		EqualsVerifier.simple().forClass(PensionerDetail.class).verify();
	}
	
	@Test
	void testPensionDetails() {
		EqualsVerifier.simple().forClass(PensionDetail.class).verify();
	}

	@Test
	void testPensionerInputDetails() {
		EqualsVerifier.simple().forClass(PensionerInput.class).verify();
	}
	
	@Test
	void testProcessPensionerInputDetails() {
		EqualsVerifier.simple().forClass(ProcessPensionInput.class).verify();
	}
	
	@Test
	void testProcessPensionResponseDetails() {
		EqualsVerifier.simple().forClass(ProcessPensionResponse.class).verify();
	}

	@Test
	void testMainMethod() throws NumberFormatException, IOException, NotFoundException, ParseException {
		ProcessPensionMicroserviceApplication.main(new String[] {});
	}

}