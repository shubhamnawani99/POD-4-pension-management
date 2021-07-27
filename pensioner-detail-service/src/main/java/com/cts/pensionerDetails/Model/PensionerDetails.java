package com.cts.pensionerDetails.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sreekant Gantela This is the Model package PensionerDetails class
 *         which consist of pensioner details like Name, Date of Birth, PAN,
 *         Salary, Allowance Pension Type, Bank Details.
 *
 *         In lombok is used to generate setters, getters and all arguments
 *         constructors
 *
 */
@Data
@AllArgsConstructor
public class PensionerDetails {

	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING ,pattern = "YYYY-MM-dd" , timezone="IST")
	private Date dateOfBirth;
	private String pan;
	private double salary;
	private double allowance;
	private String pensionType;
	private BankDetails bank;

}
