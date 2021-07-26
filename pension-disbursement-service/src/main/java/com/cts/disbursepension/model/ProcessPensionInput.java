package com.cts.disbursepension.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProcessPensionInput {
	@ApiModelProperty(value = "Aadhaar Number")
	private String aadhaarNumber;
	
	@ApiModelProperty(value = "Pension Amount")
	private double pensionAmount;
	
	@ApiModelProperty(value = "Bank Service Charges")
	private double bankServiceCharge;

}
