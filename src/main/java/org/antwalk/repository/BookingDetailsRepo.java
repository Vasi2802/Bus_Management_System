package org.antwalk.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;

import org.antwalk.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailsRepo extends JpaRepository<BookingDetails, Long>{

    public List<BookingDetails> findAllByEAndBookingForMonthGreaterThanEqual(Employee employee, Date date);		//after that date
    
    public List<BookingDetails> findAllByBookingForMonthGreaterThanEqualOrderByBookingForMonth(Date date);		//for all emp
    
    public List<BookingDetails> findAllByEAndBookingForMonth(Employee employee, Date date);		//for that date only		redundant
    
    public Optional<BookingDetails> findByEAndBookingForMonth(Employee employee, Date date);		//returns emp

    public List<BookingDetails> findByEAndBOrderByBookingForMonthAsc(Employee employee, Bus bus);

	public List<BookingDetails> findByEAndBOrderByBookingIdDesc(Employee employee, Bus bus);
	
    public List<BookingDetails> findByEAndBOrderByBookingForMonthDesc(Employee employee, Bus bus);

    public void deleteByB(Bus bus);

    public void deleteByE(Employee employee);
 
}
