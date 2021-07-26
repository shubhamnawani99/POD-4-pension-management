package com.cts.pensionerDetails.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cts.pensionerDetails.Exception.NotFoundException;
import com.cts.pensionerDetails.Model.BankDetails;
import com.cts.pensionerDetails.Model.PensionerDetails;
import com.cts.pensionerDetails.Util.DateUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * @author SREEKANTH GANTELA
 * 
 * Pensiondetails Service is a class which contain
 * the getPensionerDetailByAadhaarNumber function to get the pensioner details
 * 
 */
@Service
@Slf4j
public class PensionerdetailService {
	
	private Map<Long, PensionerDetails> pensionerDetails;
	
	/**
	 * Loads pensioner from the details  if it exists.
	 * After loading the details, compares the given Aadhaar Number from  the Detials CSV Files.
	 * 
	 * @param Aadhaar Number
	 * @return Pensioner Details
	 */
	public PensionerDetails getPensionerDetailByAadhaarNumber(long aadhaarNumber)
			throws IOException, NotFoundException, NumberFormatException, ParseException {
		
		String line = "";
		pensionerDetails = new HashMap<>();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.getClass().getResourceAsStream("/details.csv")));
		while ((line = br.readLine()) != null) // returns a Boolean value
		{
			log.info("loop inside");
			String[] person = line.split(",");
			PensionerDetails pd = new PensionerDetails(person[1],DateUtil.parseDate(person[2]), person[3],
					Double.parseDouble(person[4]), Double.parseDouble(person[5]), person[6],
					new BankDetails(person[7], Long.parseLong(person[8]), person[9]));
			pensionerDetails.put(Long.parseLong(person[0]), pd);
		}

		if (pensionerDetails.containsKey(aadhaarNumber)) {
			log.info("details found");
			return pensionerDetails.get(aadhaarNumber);
		} else {
			log.info("throws exception");
			throw new NotFoundException("Aadhar Number Not Found");
		}
	}

}
