package com.cts.authorization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Model class for user credentials
 * 
 * @author Shubham Nawani
 *
 */

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_credentials")
public class User {
	@Id
	private String username;
	@Column(nullable = false)
	private String password;
	@Column
	private String role;
}
