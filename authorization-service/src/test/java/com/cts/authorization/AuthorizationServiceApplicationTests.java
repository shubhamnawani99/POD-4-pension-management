package com.cts.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.authorization.service.UserServiceImpl;

@SpringBootTest
class AuthorizationServiceApplicationTests {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Test
	void contextLoads() {
		AuthorizationServiceApplication.main(new String[] {});
		assertThat(userServiceImpl).isNotNull();
	}

}
