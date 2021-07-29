package com.cts.authorization.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.authorization.exception.InvalidTokenException;
import com.cts.authorization.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Test cases for JWT Utils class
 * 
 * @author Shubham Nawani
 *
 */
@SpringBootTest
@Slf4j
class JWTUtilTests {

	@Autowired
	JwtUtil jwtUtil;

	@Test
	void jwtUtilNotNull()
	{
		assertNotNull(jwtUtil);
	}
	
	@Test
	@DisplayName("This method is responsible to test isTokenExpiredOrInvalidFormat()")
	void testIsTokenExpiredOrInvalidFormat_validToken() {
		log.info("START - testIsTokenExpiredOrInvalidFormat_validToken()");

		// subject of our token
		final String username = "admin1";

		// generate our token
		final String token = jwtUtil.generateToken(username);
		log.info("Token: {}", token);

		// Test the token validity
		assertFalse(jwtUtil.isTokenExpiredOrInvalidFormat(token));

		// Token should not be null
		assertNotNull(token);

		log.info("END - testIsTokenExpiredOrInvalidFormat_validToken()");
	}

	@Test
	@DisplayName("This method is responsible to test isTokenExpiredOrInvalidFormat() for Expired token")
	void testIsTokenExpiredOrInvalidFormat_expiredToken() {
		log.info("START - testIsTokenExpiredOrInvalidFormat_expiredToken()");

		// generate our token
		final String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzE4MTgsInN1YiI6ImFkbWluMSIsImV4cCI6MTYyNzAzMTg3OH0.iBDf8UvcnHKa-TVHHxjOQUiC3oEVGgsYrJSvD5LhUQc";
		log.info("Expired Token: {}", token);

		// Test the token validity
		InvalidTokenException thrownException = assertThrows(InvalidTokenException.class, () -> jwtUtil.isTokenExpiredOrInvalidFormat(token));
		assertTrue(thrownException.getMessage().contains("Token has been expired"));

		// Token should not be null
		assertNotNull(token);

		log.info("END - testIsTokenExpiredOrInvalidFormat_expiredToken()");
	}

	@Test
	@DisplayName("This method is responsible to test isTokenExpiredOrInvalidFormat() for Token with Invalid Format")
	void testIsTokenExpiredOrInvalidFormat_invalidFormatToken() {
		log.info("START - testIsTokenExpiredOrInvalidFormat_invalidFormatToken()");

		// generate our token
		final String token = "eyJhbGOiJIUzI1NiJ9.eyJpYXQiOjE2MjcwMzE4MTgsInN1YiI6ImFkbWluMSIsImV4cCI6MTYyNzAzMTg3OH0.iBDf8UvcnHKa-TVHHxjOQUiC3oEVGgsYrJSvD5LhUQc";
		log.info("Malformed Token: {}", token);

		// Test the token validity
		InvalidTokenException thrownException = assertThrows(InvalidTokenException.class, () -> jwtUtil.isTokenExpiredOrInvalidFormat(token));
		assertTrue(thrownException.getMessage().contains("Token is in invalid format"));

		// Token should not be null
		assertNotNull(token);

		log.info("END - testIsTokenExpiredOrInvalidFormat_invalidFormatToken()");
	}
	
	@Test
	@DisplayName("This method is responsible to test isTokenExpiredOrInvalidFormat() for Null token")
	void testIsTokenExpiredOrInvalidFormat_nullToken() {
		log.info("START - testIsTokenExpiredOrInvalidFormat_nullToken()");

		// generate our token
		final String token = null;
		log.info("Null Token: {}", token);

		// Test the token validity
		InvalidTokenException thrownException = assertThrows(InvalidTokenException.class, () -> jwtUtil.isTokenExpiredOrInvalidFormat(token));
		assertTrue(thrownException.getMessage().contains("Token is either null or empty"));

		// Token should not be null
		assertNull(token);

		log.info("END - testIsTokenExpiredOrInvalidFormat_nullToken()");
	}
	
	@Test
	@DisplayName("This method is responsible to test isTokenExpiredOrInvalidFormat() for Invalid token signature")
	void testIsTokenExpiredOrInvalidFormat_invalidTokenSignature() {
		log.info("START - testIsTokenExpiredOrInvalidFormat_invalidTokenSignature()");

		// generate our token
		final String token = "eyJhbGciOiJIUzI1NiJ91.eyJpYXQiOjE2MjczMjA2ODIsInN1YiI6ImFkbWluMSIsImV4cCI6MTYyNzMyMDc0Mn0.tiQjNTsiLwo7Q2EyuJeV9p187jUZVr7PCTZMs9gvBgk";
		log.info("Invalid token signature Token: {}", token);

		// Test the token validity
		InvalidTokenException thrownException = assertThrows(InvalidTokenException.class, () -> jwtUtil.isTokenExpiredOrInvalidFormat(token));
		assertTrue(thrownException.getMessage().contains("Token signature is invalid"));

		// Token should not be null
		assertNotNull(token);

		log.info("END - testIsTokenExpiredOrInvalidFormat_invalidTokenSignature()");
	}
	
	@Test
	@DisplayName("This method is responsible to test GetUsernameFromToken()")
	void testGetUsernameFromToken() {
		log.info("START - testGetUsernameFromToken()");

		// Set the username
		final String username = "admin1";

		// Generate the token
		String token = jwtUtil.generateToken(username);
		log.info("Token: {}", token);

		// Username should be equal
		assertEquals(username, jwtUtil.getUsernameFromToken(token));

		log.info("END - testGetUsernameFromToken()");
	}

}
