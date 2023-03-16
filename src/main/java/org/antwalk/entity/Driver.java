package org.antwalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="driver")
public class Driver {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="did")
	private long did;
	
	@Column(name="driver_name")
	private String driverName;

	@Column(name="driver_contact")
	private String driverContactNo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "auth_id", referencedColumnName = "id")
	@JsonBackReference
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getDid() {
		return did;
	}

	public void setDid(long did) {
		this.did = did;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	

	

	public String getDriverContactNo() {
		return driverContactNo;
	}

	public void setDriverContactNo(String driverContactNo) {
		this.driverContactNo = driverContactNo;
	}

	public Driver(long did, String driverName, String driverContactNo) {
		super();
		this.did = did;
		this.driverName = driverName;
		this.driverContactNo = driverContactNo;
	}

	public Driver() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
