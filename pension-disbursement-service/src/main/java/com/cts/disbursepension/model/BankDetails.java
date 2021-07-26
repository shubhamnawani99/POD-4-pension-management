package com.cts.disbursepension.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class BankDetails {
	private String bankName;
	private long accountNumber;
	private String bankType;
}
