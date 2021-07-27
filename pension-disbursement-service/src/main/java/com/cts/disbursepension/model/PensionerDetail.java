package com.cts.disbursepension.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for pensioner details
 * 
 * @author Anas Zubair
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class PensionerDetail {
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd", timezone = "IST")
	private Date dateOfBirth;
	private String pan;
	private double salary;
	private double allowance;
	private String pensionType;
	private BankDetails bank;
}
