package org.antwalk.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.rest.api.v2010.account.Message;

@RestController
public class SosService {

	@GetMapping(value = "/employee/sendsos")
	public ResponseEntity<String> sendSMS(@RequestParam("lat") String field1, @RequestParam("longitu") String field2) {

		System.out.println("lat:" + field1);
		System.out.println("long:" + field2);

		String message = "\n\nSOS- This is and emergency situation. I need your assistance I am currently at\n"
				+ "https://maps.google.com/?q=" + field1 + "," + field2;

		
		/*
		 * Twilio.init("ACe47c6bb7cc7045bdd61431d95b4d297d",
		 * "24396c2c47e9d612181a5fb4133e217e");
		 * 
		 * Message.creator(new com.twilio.type.PhoneNumber("+91"+"8167391883"), new
		 * com.twilio.type.PhoneNumber("+15076160721"), message).create();
		 */
		 

		return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
	}
}
