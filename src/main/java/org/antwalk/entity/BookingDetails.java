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

@Entity
@Table(name="booking_details")
public class BookingDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="booking_id")
	private long bookingId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_id", referencedColumnName = "eid")
	private Employee e;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bus_id", referencedColumnName = "bid")
	private Bus b;
	
	@Column(name="booking_for_month")
	private Date bookingForMonth;

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

	public BookingDetails(long bookingId, Employee e, Bus b, Date bookingForMonth) {
		super();
		this.bookingId = bookingId;
		this.e = e;
		this.b = b;
		this.bookingForMonth = bookingForMonth;
	}

	public BookingDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(b, bookingForMonth, bookingId, e);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingDetails other = (BookingDetails) obj;
		return Objects.equals(b, other.b) && Objects.equals(bookingForMonth, other.bookingForMonth)
				&& bookingId == other.bookingId && Objects.equals(e, other.e);
	}
	
	
	
	
}
