package org.antwalk.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="admin")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="aid")
	private long aid;
	
	@Column(name="contactNo")
	private String contactNo;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "auth_id", referencedColumnName = "id")
	private User user;
	
	
	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}



	public Admin(long aid, String name, String email, String password) {
		super();
		this.aid = aid;
	}

	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Admin(long aid, String contactNo, User user) {
		super();
		this.aid = aid;
		this.contactNo = contactNo;
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(aid, contactNo, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
		return aid == other.aid && Objects.equals(contactNo, other.contactNo) && Objects.equals(user, other.user);
	}
	
	
}
