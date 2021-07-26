package com.cts.disbursepension.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessPensionInput {
	@ApiModelProperty(value = "Aadhaar Number")
	private String aadhaarNumber;
	
	@ApiModelProperty(value = "Pension Amount")
	private double pensionAmount;
	
	@ApiModelProperty(value = "Bank Service Charges")
	private double bankServiceCharge;

}
