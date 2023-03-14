package org.antwalk.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.antwalk.repository.BookingDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BookingDetailsService {

	@Autowired
	private BookingDetailsRepo bookingRepo;

	public BookingDetails insertBookingDetails(BookingDetails bd) {
		return bookingRepo.save(bd);
	}

	public List<BookingDetails> getAllBookingDetails() {
		return bookingRepo.findAll();
	}

	public BookingDetails getBookingDetailsById(long id) {
		return bookingRepo.findById(id).get();
	}

	public String deleteBookingDetailsById(long id) {
		bookingRepo.deleteById(id);
		return "Booking Details Deleted";
	}

	public String updateBookingDetailsById(BookingDetails bd, long id) {
		List<BookingDetails> bookingList = bookingRepo.findAll();
		for (BookingDetails obj : bookingList) {
			if (obj.getBookingId() == id) {
				if (bd.getBookingId() == id) {
					bookingRepo.save(bd);
					return "Booking Details Updated";
				}

				else {
					return "Booking Details exists but your input id does not match with the existing Booking Details id";
				}

			}
		}
		return "Booking Details does not exist";

	}

	// GET BOOKING PER MONTH FOR CURRENT YEAR
	/*
	 * Parameters - None
	 * Returns (MonthName-> Count for month) as Map<String, Integer>
	 */
	public List<List<Object>> getBookingPerMonth() {
		LocalDate januaryFirst = LocalDate.now().withDayOfYear(1);
		List<BookingDetails> bookingDetailsList = bookingRepo
				.findAllByBookingForMonthGreaterThanEqualOrderByBookingForMonth(Date.valueOf(januaryFirst));
		LocalDate curLastDate;
		LocalDate curMonth;
		List<List<Object>> monthFreq = new ArrayList<>();
		int j = 0;
		for (int i = 1; i < 13; i++) {
			curMonth = LocalDate.now().withMonth(i);
			curLastDate = curMonth.withDayOfMonth(curMonth.lengthOfMonth());
			String monthName = curMonth.getMonth().name().substring(0, 3);
			int freq = 0;
			while(j<bookingDetailsList.size()){
				BookingDetails bookingDetails = bookingDetailsList.get(j);
				if (bookingDetails.getBookingForMonth().toLocalDate().getMonthValue()<=i) {
							freq += 1;
							j += 1;
				}
				else{
					break;
				}
			}
			List<Object> nameAndFreq = new ArrayList<>();
			nameAndFreq.add(monthName);
			nameAndFreq.add(freq);
			monthFreq.add(nameAndFreq);
		}
		// System.out.println(monthFreq.toString());

		return monthFreq;
	}

    public List<BookingDetails> getAllBookingDetailsForPeriod(LocalDate firstDate, LocalDate lastdate) {
		firstDate = firstDate.minusDays(1);
		lastdate = lastdate.plusDays(1);
		List<BookingDetails> bookingDetailsList = new ArrayList<>();
		for(BookingDetails bookingDetails: getAllBookingDetails()){
			if(bookingDetails.getBookingForMonth().after(Date.valueOf(firstDate)) && 
			bookingDetails.getBookingForMonth().before(Date.valueOf(lastdate))){
				bookingDetailsList.add(bookingDetails);
			}
		}
		return bookingDetailsList;
    }

    public BookingDetails findMostRecentBooking(Employee employee, Bus bus) {
        List<BookingDetails> bookingDetailsList = bookingRepo.findByEAndBOrderByBookingForMonthDesc(employee, bus);
		if(bookingDetailsList.size()>0)
			return bookingDetailsList.get(0);
		else
			return null;
    }


}
