package org.antwalk.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stop")
public class Stop {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sid")
	private long sid;
	
	@Column(name="name",unique=true)
	private String name;

	
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Stop(long sid, String name) {
		super();
		this.sid = sid;
		this.name = name;
	}
	public Stop() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Stop(String name) {
		super();
		this.name = name;
	}	
	
	
}
