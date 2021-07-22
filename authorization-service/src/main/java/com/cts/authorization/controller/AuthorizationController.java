package com.cts.authorization.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.authorization.exception.InvalidCredentialsException;
import com.cts.authorization.model.UserRequest;
import com.cts.authorization.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Authorization Controller to handle requests for logging in a valid user and
 * validating the JWT Token for other services.
 * 
 * @author Shubham Nawani
 * 
 */
@RestController
@Slf4j
public class AuthorizationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserDetailsService userService;
	

	/**
	 * @URL: http://localhost:8081/login
	 * 
	 * @Data: [Admin] { "username": "admin1", "password": "adminpass@1234" }
	 * 
	 * @param userRequest {username, password}
	 * @return token on successful login else throws exception handled by
	 *         GlobalExceptionHandler
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid UserRequest userRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		} catch (BadCredentialsException | DisabledException | LockedException e) {
			throw new InvalidCredentialsException(e.getMessage());
		}
		String token = jwtUtil.generateToken(userRequest.getUsername());
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	/**
	 * @URL: http://localhost:8081/statusCheck
	 * @return "OK" if the server and controller is up and running
	 */
	@GetMapping(value = "/statusCheck")
	public String statusCheck() {
		log.info("OK");
		return "OK";
	}

	/**
	 * Checks if the token is a valid administrator token
	 * 
	 * @URL: http://localhost:8081/validate
	 * 
	 * @Header: [Authorization] = JWT Token
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validateAdmin(@RequestHeader(name = "Authorization") String token) {

		if (!jwtUtil.isTokenExpiredOrInvalidFormat(token)) {
			UserDetails user = userService.loadUserByUsername(jwtUtil.getUsernameFromToken(token));
			if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
				return new ResponseEntity<>(true, HttpStatus.OK);
			else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
	}

}
