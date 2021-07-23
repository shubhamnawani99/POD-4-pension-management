package com.cts.pensionerDetails.Controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.pensionerDetails.Exception.NotFoundException;
import com.cts.pensionerDetails.Model.PensionerDetails;
import com.cts.pensionerDetails.Service.PensionerdetailService;

@RestController
public class PensionerDetailsController {
	
	@Autowired
	private PensionerdetailService pds;
	
	@GetMapping("/pensionerDetailByAadhaar/{aadhaarNumber}")
	public PensionerDetails getPensionerDetailByAadhaar(@PathVariable long aadhaarNumber ) throws NotFoundException {

		try {
			return pds.getPensionerDetailByAadhaarNumber(aadhaarNumber);
		} catch (NumberFormatException | IOException | NotFoundException | ParseException e) {
			throw new NotFoundException("AadharNumber Not Found");
		}

	}

}
