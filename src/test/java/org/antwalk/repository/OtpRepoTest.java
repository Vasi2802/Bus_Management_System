package org.antwalk.repository;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalTime;

import org.antwalk.entity.Otp;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.antwalk.entity.Otp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OtpRepoTest {

	@MockBean
    private OtpRepo otpRepo;
	
	private String email;
	private String otpValue;
	
	@BeforeEach
	void setUp() {
		email = "employee@nrifintech.com";
		otpValue = "fun123";
	}

	
	@Test
	void testFindByEmail() {
		Otp expected = new Otp(1, email,otpValue);
		when(otpRepo.findByEmail(email)).thenReturn(expected);
		Otp actual= otpRepo.findByEmail(email);
		assertEquals(expected, actual);

	}

	@Test
	void testFindByOtpValue() {
		Otp expected = new Otp(4, email,otpValue);
		when(otpRepo.findByOtpValue(otpValue)).thenReturn(expected);
		Otp actual= otpRepo.findByOtpValue(otpValue);
		assertEquals(expected, actual);
	}

}
