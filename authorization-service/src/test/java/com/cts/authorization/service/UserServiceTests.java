package com.cts.authorization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cts.authorization.exception.InvalidCredentialsException;
import com.cts.authorization.model.User;
import com.cts.authorization.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Test cases for user service
 * 
 * @author Shubham Nawani
 *
 */
@SpringBootTest
@Slf4j
class UserServiceTests {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@MockBean
	private UserRepository userRepository;

	@Value("${userDetails.errorMessage}")
	private String ERROR_MESSAGE;
	
	@Test
	@DisplayName("This method is responsible to test LoadUserByUsername() method when username is valid")
	void testLoadUserByUsername_validUsername() {
		log.info("START - testLoadUserByUsername_validUsername()");

		// Data to mock
		User user = new User("admin1", "$2a$10$aMMcsBB18R7dqzC7Wcg3z.oiVQnNhgFGD0WMTZVeVtFCMMnru25AO", "ADMIN");

		// Convert to optional
		Optional<User> userOptional = Optional.of(user);

		// user-name to check - correct credentials
		final String id = "admin1";

		// actual value
		SecurityUser securityUser = new SecurityUser(id, user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));

		// Mock the repository
		when(userRepository.findById(id)).thenReturn(userOptional);

		log.info("Running the test case...");
		// checking condition
		assertEquals(userServiceImpl.loadUserByUsername(id), securityUser);
		assertNotNull(securityUser);

		log.info("END - testLoadUserByUsername_validUsername()");
	}

	@Test
	@DisplayName("This method is responsible to test LoadUserByUsername() method when username is invalid")
	void testLoadUserByUsername_invalidUsername() {
		log.info("START - testLoadUserByUsername_invalidUsername()");

		User user = new User("admin1", "$2a$10$aMMcsBB18R7dqzC7Wcg3z.oiVQnNhgFGD0WMTZVeVtFCMMnru25AO", "ADMIN");

		// Data to mock
		Optional<User> userOptional = Optional.empty();

		// user-name to check - incorrect credentials
		final String id = "admin404";

		// actual value
		SecurityUser securityUser = new SecurityUser(id, user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));

		// Mock the repository
		when(userRepository.findById(id)).thenReturn(userOptional);

		log.info("Running the test case...");
		// checking condition
		InvalidCredentialsException thrownException = assertThrows(InvalidCredentialsException.class,
				() -> userServiceImpl.loadUserByUsername(id));
		
		assertTrue(thrownException.getMessage().contains(ERROR_MESSAGE));
		assertNotNull(securityUser);

		log.info("END - testLoadUserByUsername_invalidUsername()");
	}

	// Class to avoid User conflict
	@MockBean
	public class SecurityUser extends org.springframework.security.core.userdetails.User {

		private static final long serialVersionUID = -4209816021578748288L;

		public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}

	}
}
