package org.antwalk.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="waiting_list")
public class WaitingList {

	@Id
	@GeneratedValue
	@Column(name="waiting_id")
	private long wid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_id", referencedColumnName = "eid")
	private Employee e;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bus_id", referencedColumnName = "bid")
	private Bus b;

	public long getWid() {
		return wid;
	}

	public void setWid(long wid) {
		this.wid = wid;
	}

	public Employee getE() {
		return e;
	}

	public void setE(Employee e) {
		this.e = e;
	}

	public Bus getB() {
		return b;
	}

	public void setB(Bus b) {
		this.b = b;
	}

	public WaitingList(long wid, Employee e, Bus b) {
		super();
		this.wid = wid;
		this.e = e;
		this.b = b;
	}

	public WaitingList() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(b, e, wid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WaitingList other = (WaitingList) obj;
		return Objects.equals(b, other.b) && Objects.equals(e, other.e) && wid == other.wid;
	}
	
	
	
}
