package org.antwalk.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


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

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "auth_id", referencedColumnName = "id")
	private User user;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "d")
	private Bus bus;
	
	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	
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

	public Driver(long did, String driverName, String driverEmail, String driverContactNo) {
		super();
		this.did = did;
		this.driverName = driverName;
		this.driverContactNo = driverContactNo;
	}

	public Driver() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Driver(String driverName, String driverContactNo, User user, Bus bus) {
		super();
		this.driverName = driverName;
		this.driverContactNo = driverContactNo;
		this.user = user;
		this.bus = bus;
	}
	
	

	public Driver(long did, String driverName, String driverContactNo, User user) {
		super();
		this.did = did;
		this.driverName = driverName;
		this.driverContactNo = driverContactNo;
		this.user = user;
	}

	
	
}
