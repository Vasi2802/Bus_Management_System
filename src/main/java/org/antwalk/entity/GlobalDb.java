package org.antwalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="global_db")
public class GlobalDb {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="gid")
	private long gid;
	
	@Column(name="username")
	private String email;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public long getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid;
	}

	public GlobalDb(long aid, String name, String email, String password) {
		super();
		this.email = email;
	}

	public GlobalDb() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalDb(long gid, String email) {
		super();
		this.gid = gid;
		this.email = email;
	}

	
	
	
}
