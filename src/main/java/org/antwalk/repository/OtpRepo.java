package org.antwalk.repository;
import org.antwalk.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<Otp, Long> {
	public Otp findByEmail(String email);
	public Otp findByOtpValue(String otpValue);
}
