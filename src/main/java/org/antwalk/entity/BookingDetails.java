package org.antwalk.entity;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="booking_details")
public class BookingDetails implements Comparable<BookingDetails>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_id")
	private long bookingId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_id", referencedColumnName = "eid")
	@NotNull
	private Employee e;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bus_id", referencedColumnName = "bid")
	@NotNull
	@JsonBackReference
	private Bus b;
	
	@Column(name="booking_for_month")
	@NotNull
	private Date bookingForMonth;

	@Column(name="is_boarded")
	private Boolean isBoarded;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="stop_id", referencedColumnName = "sid")
	private Stop stop;
	
	public BookingDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BookingDetails(long bookingId, Employee e, Bus b, Date bookingForMonth) {
		super();
		this.bookingId = bookingId;
		this.e = e;
		this.b = b;
		this.bookingForMonth = bookingForMonth;
	}

	public BookingDetails(long bookingId, @NotNull Employee e, @NotNull Bus b, @NotNull Date bookingForMonth,
			Stop stop) {
		this.bookingId = bookingId;
		this.e = e;
		this.b = b;
		this.bookingForMonth = bookingForMonth;
		this.stop = stop;
	}

	

	public BookingDetails(long bookingId, @NotNull Employee e, @NotNull Bus b, @NotNull Date bookingForMonth,
			Boolean isBoarded, Stop stop) {
		this.bookingId = bookingId;
		this.e = e;
		this.b = b;
		this.bookingForMonth = bookingForMonth;
		this.isBoarded = isBoarded;
		this.stop = stop;
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
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

	public Date getBookingForMonth() {
		return bookingForMonth;
	}

	public void setBookingForMonth(Date bookingForMonth) {
		this.bookingForMonth = bookingForMonth;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}


	@Override
	public int compareTo(BookingDetails o) {
		// TODO Auto-generated method stub
		return this.bookingForMonth.compareTo(o.getBookingForMonth());
	}

	public Boolean getIsBoarded() {
		return isBoarded;
	}

	public void setIsBoarded(Boolean isBoarded) {
		this.isBoarded = isBoarded;
	}

	

	
}
