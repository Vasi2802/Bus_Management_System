package org.antwalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="otp")
public class Otp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="oid")
	private long oid;
	
	@Column(name="email")
	private String email;
	
	@Column(name="otp")
	private String otpValue;

	public Otp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Otp(long oid, String email, String otpValue) {
		super();
		this.oid = oid;
		this.email = email;
		this.otpValue = otpValue;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtpValue() {
		return otpValue;
	}

	public void setOtpValue(String otpValue) {
		this.otpValue = otpValue;
	}
}
