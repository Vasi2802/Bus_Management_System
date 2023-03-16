package org.antwalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="aid")
	private long aid;
	
	@Column(name="username")
	private String email;
	
	@Column(name="password")
	private String password;

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Admin(long aid, String name, String email, String password) {
		super();
		this.aid = aid;
		this.email = email;
		this.password = password;
	}

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
