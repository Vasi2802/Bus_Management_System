package org.antwalk.entity;

import java.time.LocalTime;
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
@Table(name="bus")
public class Bus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bid")
	private long bid;
	
	@Column(name="total_seats")
	private int totalSeats;
	
	@Column(name="available_seats")
	private int availableSeats;
	
	@Column(name="start_time")
	private LocalTime startTime;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver_id", referencedColumnName = "did")
	private Driver d;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bus_route", referencedColumnName = "rid")
	private Route r;

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public Driver getD() {
		return d;
	}

	public void setD(Driver d) {
		this.d = d;
	}

	public Route getR() {
		return r;
	}

	public void setR(Route r) {
		this.r = r;
	}

	public Bus(long bid, int totalSeats, int availableSeats, LocalTime startTime, Driver d, Route r) {
		super();
		this.bid = bid;
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
		this.startTime = startTime;
		this.d = d;
		this.r = r;
	}

	public Bus() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Bus [bid=" + bid + ", totalSeats=" + totalSeats + ", availableSeats=" + availableSeats + ", startTime="
				+ startTime + ", d=" + d + ", r=" + r + "]";
	}

	public Bus(int totalSeats, int availableSeats, LocalTime startTime, Driver d, Route r) {
		super();
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
		this.startTime = startTime;
		this.d = d;
		this.r = r;
	}

	@Override
	public int hashCode() {
		return Objects.hash(availableSeats, bid, d, r, startTime, totalSeats);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bus other = (Bus) obj;
		return availableSeats == other.availableSeats && bid == other.bid && Objects.equals(d, other.d)
				&& Objects.equals(r, other.r) && Objects.equals(startTime, other.startTime)
				&& totalSeats == other.totalSeats;
	}

	
	

}
