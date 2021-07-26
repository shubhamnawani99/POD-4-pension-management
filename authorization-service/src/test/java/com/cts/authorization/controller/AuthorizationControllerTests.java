package com.cts.authorization.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

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

	@MockBean
	private UserServiceImpl userServiceImpl;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtUtil jwtUtil;
	
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
		mockMvc.perform(
					get("/validate")
					.header(HttpHeaders.AUTHORIZATION, token)
				)
				.andExpect(status().isOk());
		
		log.info("END - testValidateAdmin_withValidTokenAndRole()");
	}

	@Test
	@DisplayName("This method is responsible to test validateAdmin() method with invalid/expired token")
	void testValidate_withInvalidToken() throws Exception {
		log.info("START - testValidate_withInvalidToken()");

		// mock certain functionalities to load user and have a invalid token
		when(userServiceImpl.loadUserByUsername(ArgumentMatchers.any())).thenReturn(validUser);
		when(jwtUtil.isTokenExpiredOrInvalidFormat(ArgumentMatchers.any())).thenReturn(true);
		
		// set the invalid token
		String token = "Bearer fyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzk2NzcsInN1YiI6ImFkbWluMSIsImV4cCI6MTY1ODU3NTY3N30.trkCUngtLG8C1W6obvcGvQhCK1J9qg2Hsbcn8GJB95Y";
		log.info("Token: {}", token);
		
		// perform the mock
		mockMvc.perform(
					get("/validate")
					.header(HttpHeaders.AUTHORIZATION, token)
				)
				.andExpect(status().isBadRequest());
		
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
		mockMvc.perform(
					get("/validate")
					.header(HttpHeaders.AUTHORIZATION, token)
				)
				.andExpect(status().isUnauthorized());
		
		log.info("END - testValidate_withInvalidRole()");
	}
	
	// Class to avoid User conflict
	public class SecurityUser extends org.springframework.security.core.userdetails.User {

		private static final long serialVersionUID = -4209816021578748288L;

		public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}

	}
}
