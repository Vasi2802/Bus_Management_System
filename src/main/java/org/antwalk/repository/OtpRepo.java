package org.antwalk.repository;
import org.antwalk.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<Otp, Long> {
		Otp findByEmail(String email);
		Otp findByOtpValue(String otpValue);
}
