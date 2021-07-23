package com.cts.pensionerDetails.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sreekant Gantela
 * This is the Model package PensionerDetails class which consist 
 * of pensioner details like Name, Date of Birth, PAN, Salary, Allowance
 * Pension Type, Bank Details.
 *
 *In lombok is used to generate no arugument, all arguments constructors 
 *entity and columns.
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PensionerDetails {
	
	private String name;
	private Date dateOfBirth;
	private String pan;
	private double salary;
	private double allowance;
	private String pensionType;
	private BankDetails bank;


}
