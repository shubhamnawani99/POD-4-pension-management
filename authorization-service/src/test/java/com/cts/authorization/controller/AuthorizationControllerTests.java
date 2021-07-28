package com.cts.authorization.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cts.authorization.exception.InvalidTokenException;
import com.cts.authorization.model.UserRequest;
import com.cts.authorization.service.UserServiceImpl;
import com.cts.authorization.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Test cases for the authorization controller
 * 
 * @author Shubham Nawani
 *
 */
@WebMvcTest
@Slf4j
class AuthorizationControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userServiceImpl;

	@MockBean
	private JwtUtil jwtUtil;

	@MockBean
	private AuthenticationManager authenticationManager;

	@Value("${userDetails.errorMessage}")
	private String ERROR_MESSAGE;
	
	@Value("${userDetails.badCredentialsMessage}")
	private String BAD_CREDENTIALS_MESSAGE;

	@Value("${userDetails.disabledAccountMessage}")
	private String DISABLED_ACCOUNT_MESSAGE;
	
	@Value("${userDetails.lockedAccountMessage}")
	private String LOCKED_ACCOUNT_MESSAGE;
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static SecurityUser validUser;
	private static SecurityUser invalidUser;

	@BeforeEach
	void generateUserCredentials() {
		// User object
		validUser = new SecurityUser("admin1", "$2a$10$aMMcsBB18R7dqzC7Wcg3z.oiVQnNhgFGD0WMTZVeVtFCMMnru25AO",
				Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
		invalidUser = new SecurityUser("admin1", "$2a$10$aMMcsBB18R7dqzC7Wcg3z.oiVQnNhgFGD0WMTZVeVtFCMMnru25AO",
				Collections.singletonList(new SimpleGrantedAuthority("USER")));
	}

	/*****************************************************************
	 * User Login Tests
	 * 
	 * @throws Exception
	 * 
	 *****************************************************************
	 */
	@Test
	@DisplayName("This method is responsible to test login() method with valid credentials")
	void testLogin_withValidCredentials() throws Exception {
		log.info("START - testLogin_withValidCredentials()");

		// Set the user request
		UserRequest user = new UserRequest("admin1", "adminpass@1234");

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzk2NzcsInN1YiI6ImFkbWluMSIsImV4cCI6MTY1ODU3NTY3N30.trkCUngtLG8C1W6obvcGvQhCK1J9qg2Hsbcn8GJB95Y";
		log.info("Token: {}", token);

		// mock certain functionalities to return a valid user and generate the token
		when(authenticationManager.authenticate(ArgumentMatchers.any()))
				.thenReturn(new TestingAuthenticationToken("admin1", "adminpass@1234", "ADMIN"));
		when(jwtUtil.generateToken(ArgumentMatchers.any())).thenReturn(token);

		String json = mapper.writeValueAsString(user);
		log.info("Input data {}", json);

		MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(json).accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		assertNotNull(contentAsString);
		// match the token from the response body
		assertEquals(contentAsString, token);

		log.info("END - testLogin_withValidCredentials()");
	}

	@Test
	@DisplayName("This method is responsible to test login() method with Global Input Errors")
	void testLogin_withGlobalExceptions() throws Exception {
		log.info("START - testLogin_withGlobalExceptions()");
		UserRequest user = new UserRequest("1", "adminpass@1234");

		final String errorMessage = "Invalid Credentials";
				
		String json = mapper.writeValueAsString(user);
		log.info("Input data {}", json);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message", Matchers.equalTo(errorMessage)));
		
		log.info("END - testLogin_withGlobalExceptions()");
	}

	@Test
	@DisplayName("This method is responsible to test login() method with invalid credentials")
	void testLogin_withInvalidCredentials() throws Exception {
		log.info("START - testLogin_withInvalidCredentials()");

		// Set the user request and role
		UserRequest user = new UserRequest("admin404", "adminpass@1234");
		
		// mock certain functionalities to return a valid user and generate the token
		when(authenticationManager.authenticate(ArgumentMatchers.any())).thenThrow(new BadCredentialsException(ERROR_MESSAGE));
		
		String json = mapper.writeValueAsString(user);
		log.info("Input data {}", json);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message", Matchers.equalTo(ERROR_MESSAGE)));

		log.info("END - testLogin_withInvalidCredentials()");
	}
	
	@Test
	@DisplayName("This method is responsible to test login() method with locked account credentials")
	void testLogin_withLockedCredentials() throws Exception {
		log.info("START - testLogin_withLockedCredentials()");

		// Set the user request and role
		UserRequest user = new UserRequest("admin405", "adminpass@1234");
		
		// mock certain functionalities to return a valid user and generate the token
		when(authenticationManager.authenticate(ArgumentMatchers.any())).thenThrow(new LockedException(LOCKED_ACCOUNT_MESSAGE));
		
		String json = mapper.writeValueAsString(user);
		log.info("Input data {}", json);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message", Matchers.equalTo(LOCKED_ACCOUNT_MESSAGE)));

		log.info("END - testLogin_withLockedCredentials()");
	}
	
	@Test
	@DisplayName("This method is responsible to test login() method with disabled account credentials")
	void testLogin_withDisabledAccountCredentials() throws Exception {
		log.info("START - testLogin_withDisabledAccountCredentials()");

		// Set the user request and role
		UserRequest user = new UserRequest("admin406", "adminpass@1234");
		
		// mock certain functionalities to return a valid user and generate the token
		when(authenticationManager.authenticate(ArgumentMatchers.any())).thenThrow(new DisabledException(DISABLED_ACCOUNT_MESSAGE));
		
		String json = mapper.writeValueAsString(user);
		log.info("Input data {}", json);

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(json)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message", Matchers.equalTo(DISABLED_ACCOUNT_MESSAGE)));

		log.info("END - testLogin_withDisabledAccountCredentials()");
	}
	
	/*****************************************************************
	 * Token Validation Tests
	 * 
	 * @throws Exception
	 * 
	 *****************************************************************
	 */
	@Test
	@DisplayName("This method is responsible to test validateAdmin() method with valid token")
	void testValidateAdmin_withValidTokenAndRole() throws Exception {
		log.info("START - testValidateAdmin_withValidTokenAndRole()");

		// mock certain functionalities to load user and have a valid token
		when(userServiceImpl.loadUserByUsername(ArgumentMatchers.any())).thenReturn(validUser);
		when(jwtUtil.isTokenExpiredOrInvalidFormat(ArgumentMatchers.any())).thenReturn(false);

		// set the token
		String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzk2NzcsInN1YiI6ImFkbWluMSIsImV4cCI6MTY1ODU3NTY3N30.trkCUngtLG8C1W6obvcGvQhCK1J9qg2Hsbcn8GJB95Y";
		log.info("Token: {}", token);

		// perform the mock
		mockMvc.perform(get("/validate").header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isOk());

		log.info("END - testValidateAdmin_withValidTokenAndRole()");
	}

	@Test
	@DisplayName("This method is responsible to test validateAdmin() method with invalid/expired token")
	void testValidate_withInvalidToken() throws Exception {
		log.info("START - testValidate_withInvalidToken()");
		final String errorMessage = "Token has been expired";

		// mock certain functionalities to load user and have a invalid token
		when(userServiceImpl.loadUserByUsername(ArgumentMatchers.any())).thenReturn(validUser);
		when(jwtUtil.isTokenExpiredOrInvalidFormat(ArgumentMatchers.any())).thenThrow(new InvalidTokenException(errorMessage));

		// set the invalid token
		String token = "Bearer fyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzk2NzcsInN1YiI6ImFkbWluMSIsImV4cCI6MTY1ODU3NTY3N30.trkCUngtLG8C1W6obvcGvQhCK1J9qg2Hsbcn8GJB95Y";
		log.info("Token: {}", token);

		// perform the mock
		mockMvc.perform(get("/validate").header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isBadRequest());

		log.info("END - testValidate_withInvalidToken()");
	}

	@Test
	@DisplayName("This method is responsible to test validateAdmin() method with invalid role")
	void testValidate_withInvalidRole() throws Exception {
		log.info("START - testValidate_withInvalidRole()");

		// mock certain functionalities to load invalid user and have a valid token
		when(userServiceImpl.loadUserByUsername(ArgumentMatchers.any())).thenReturn(invalidUser);
		when(jwtUtil.isTokenExpiredOrInvalidFormat(ArgumentMatchers.any())).thenReturn(false);

		// set the invalid token
		String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzk2NzcsInN1YiI6ImFkbWluMSIsImV4cCI6MTY1ODU3NTY3N30.trkCUngtLG8C1W6obvcGvQhCK1J9qg2Hsbcn8GJB95Y";
		log.info("Token: {}", token);

		// perform the mock
		mockMvc.perform(get("/validate").header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isUnauthorized());

		log.info("END - testValidate_withInvalidRole()");
	}

	
	@Test
	@DisplayName("Test method to check for status check")
	void testStatusCheck() throws Exception {
		log.info("START - testStatusCheck()");

		MvcResult result = mockMvc.perform(get("/statusCheck"))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		
		String contentAsString = result.getResponse().getContentAsString();
		
		assertEquals("OK", contentAsString);
		assertNotNull(result);
		
		log.info("END - testStatusCheck()");
	}
	
	// Class to avoid User conflict
	public class SecurityUser extends org.springframework.security.core.userdetails.User {

		private static final long serialVersionUID = -4209816021578748288L;

		public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}

	}

}
