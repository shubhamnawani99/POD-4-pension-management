package com.cts.processPension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProcessPensionApplicationTests {

	@Test
	void contextLoads() {
		ProcessPensionServiceApplication.main(new String[] {});
		assertNotNull(ProcessPensionServiceApplication.class);
	}

}