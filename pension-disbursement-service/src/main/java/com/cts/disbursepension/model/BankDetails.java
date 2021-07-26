package com.cts.disbursepension.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankDetails {
	private String bankName;
	private long accountNumber;
	private String bankType;
}
