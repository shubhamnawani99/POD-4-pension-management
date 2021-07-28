package com.cts.pensionerDetails;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pensionerDetails.Exception.NotFoundException;

@SpringBootTest
class PensionerDetailsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
	@Test
	void testMainMethod() throws NumberFormatException, IOException, NotFoundException, ParseException {
		PensionerDetailsApplication.main(new String [] {});
	}


}
