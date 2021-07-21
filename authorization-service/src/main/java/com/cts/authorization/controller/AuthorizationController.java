package com.cts.authorization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "auth")
public class AuthorizationController {

	/**
	 * @URL: http://localhost:8081/auth/statusCheck
	 * @return "OK" if the server and controller is up and running
	 */
	@GetMapping(value = "/statusCheck")
	public String statusCheck() {
		log.info("OK");
		return "OK";
	}

}
