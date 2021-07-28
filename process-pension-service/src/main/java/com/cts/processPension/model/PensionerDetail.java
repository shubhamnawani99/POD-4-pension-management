package com.cts.processPension.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Model class for pensioner details
 * 
 * @author Shubham Nawani
 *
 */
@Getter
@AllArgsConstructor
public class PensionerDetail {
	private String name;
	private Date dateOfBirth;
	private String pan;
	private double salary;
	private double allowance;
	private String pensionType;
	private Bank bank;
}