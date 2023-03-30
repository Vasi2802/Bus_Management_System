package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.BookingDetails;
import org.antwalk.service.BookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookingdetails")
public class BookingDetailsController {
	
	@Autowired
	BookingDetailsService bookingService;
	
	@PostMapping("/insert")
	public BookingDetails insert(@RequestBody BookingDetails bd) {
		return bookingService.insertBookingDetails(bd);
	}
	
	@GetMapping("/getall")
	public List<BookingDetails> getAll(){
		return bookingService.getAllBookingDetails();
	}
	
	@GetMapping("/getbyid/{id}")
	public BookingDetails getById(@PathVariable long id) {
		return bookingService.getBookingDetailsById(id);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		bookingService.deleteBookingDetailsById(id);
		return "Deleted";
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody BookingDetails bd, @PathVariable long id) {
		List<BookingDetails> bookingList = bookingService.getAllBookingDetails();
		for(BookingDetails obj:bookingList) {
			if(obj.getBookingId() == id) {
				if(bd.getBookingId() == id) {
					bookingService.insertBookingDetails(bd);
					return "Updated";
				}
				
				else {
					return "Id doesn't match";
				}
				
			}
		}
		return "Id does not exist";
		
	}
}
