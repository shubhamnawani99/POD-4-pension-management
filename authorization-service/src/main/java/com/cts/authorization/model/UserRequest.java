package com.cts.authorization.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for user request sent for logging in
 * 
 * @author Shubham Nawani
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserRequest {

	@NotBlank
	@Size(min=6, max=25)
	private String username;
	
	@NotBlank
	private String password;
}
