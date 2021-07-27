package com.cts.processPension.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PensionerInput {
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	private Date dateOfBirth;
	@NotBlank(message = "PAN Number cannot be blank")
	private String pan;
	@Size(min = 12, max = 12, message = "Aadhaar Number has invalid length")
	private String aadhaarNumber;
	@NotBlank(message = "Pension Type cannot be blank")
	private String pensionType;
	
}