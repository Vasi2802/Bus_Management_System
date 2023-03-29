package org.antwalk.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="delay")
public class Delay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="delayid")
	private long delayid;
	
	@OneToOne
	@JoinColumn(name = "bus_id", referencedColumnName = "bid")
	private Bus bus;
	
	@OneToOne
	@JoinColumn(name = "stop_id", referencedColumnName = "sid")
	private Stop stop;
	
	@Column(name="actualTime")
	@NotNull
	private LocalTime actualTime;

	public Delay() {
	}

	public Delay(Bus bus, Stop stop, @NotNull LocalTime actualTime) {
		System.out.println(bus);
		this.bus = bus;
		this.stop = stop;
		this.actualTime = actualTime;
	}

	public long getDelayid() {
		return delayid;
	}

	public void setDelayid(long delayid) {
		this.delayid = delayid;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	public LocalTime getActualTime() {
		return actualTime;
	}

	public void setActualTime(LocalTime actualTime) {
		this.actualTime = actualTime;
	}

	@Override
	public String toString() {
		return "Delay [delayid=" + delayid + ", bus=" + bus + ", stop=" + stop + ", actualTime=" + actualTime + "]";
	}


}
