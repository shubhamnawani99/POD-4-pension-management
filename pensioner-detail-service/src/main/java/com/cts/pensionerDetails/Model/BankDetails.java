package com.cts.pensionerDetails.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sreekanth Gantela
 * This is BankDetail class which contains the Bank details
 * like bankName, bankType, accountNumber
 *
 */
@Data
@AllArgsConstructor
public class BankDetails {
	
	private String bankName;
	private long accountNumber;
	private String bankType;
	

}
