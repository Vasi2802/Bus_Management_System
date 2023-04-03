package org.antwalk.entity;

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
import javax.validation.constraints.NotNull;

@Entity
@Table(name="waiting_list")
public class WaitingList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="waiting_id")
	private long wid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_id", referencedColumnName = "eid")
	@NotNull
	private Employee e;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bus_id", referencedColumnName = "bid")
	@NotNull
	private Bus b;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stop_id", referencedColumnName="sid")
	@NotNull
	private Stop stop;

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

	public WaitingList(long wid, @NotNull Employee e, @NotNull Bus b, Stop stop) {
		this.wid = wid;
		this.e = e;
		this.b = b;
		this.stop = stop;
	}

	public WaitingList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}


	
	
}
