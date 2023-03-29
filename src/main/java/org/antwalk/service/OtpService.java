package org.antwalk.service;

import java.util.Random;

import org.antwalk.entity.Otp;
import org.antwalk.repository.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private OtpRepo otpRepository;

    @Autowired
    private EmailService emailServ;
    
    private static final int OTP_LENGTH = 6;
    
    
    public Otp generateOtp(String email) {
        Otp otp = new Otp();
        otp.setEmail(email);
        String otpval = generateOtpValue();
        otp.setOtpValue(otpval);
//        emailServ.sendEmail(email, "OTP to reset password", "Otp to reset the password - "+otpval);
        return otpRepository.save(otp);
    }

    private String generateOtpValue() {
        Random random = new Random();
        int max = (int) Math.pow(10, OTP_LENGTH);
        int min = (int) Math.pow(10, OTP_LENGTH - 1);
        int otpValue = random.nextInt(max - min) + min;

        System.out.println(otpValue);
        return String.valueOf(otpValue);
    }

    public Otp getOtpByEmail(String email) {
        return otpRepository.findByEmail(email);
    }

    public void deleteOtpByEmail(String email) {
        Otp otp = otpRepository.findByEmail(email);
        
        otpRepository.delete(otp);
    }
    public String getEmailFromOtp(String otpValue) {
        Otp otp = otpRepository.findByOtpValue(otpValue);
        if (otp == null) {
            // handle case where OTP does not exist
            return null;
        }
        String email = otp.getEmail();
        return email;
    }
}
