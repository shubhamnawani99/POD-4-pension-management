package com.cts.authorization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Model class for user credentials
 * 
 * @author Shubham Nawani
 *
 */
@Data
@Entity
@Table(name = "user_credentials")
public class User {
	@Id
	private String username;
	@Column
	private String password;
	@Column
	private String role;
}
