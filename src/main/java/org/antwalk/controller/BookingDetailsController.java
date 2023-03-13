package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.BookingDetails;
import org.antwalk.repository.BookingDetailsRepo;
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
	BookingDetailsRepo bookingRepo;
	
	@PostMapping("/insert")
	public BookingDetails insert(@RequestBody BookingDetails bd) {
		return bookingRepo.save(bd);
	}
	
	@GetMapping("/getall")
	public List<BookingDetails> getAll(){
		return bookingRepo.findAll();
	}
	
	@GetMapping("/getbyid/{id}")
	public BookingDetails getById(@PathVariable long id) {
		return bookingRepo.findById(id).get();
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		bookingRepo.deleteById(id);
		return "Deleted";
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody BookingDetails bd, @PathVariable long id) {
		List<BookingDetails> bookingList = bookingRepo.findAll();
		for(BookingDetails obj:bookingList) {
			if(obj.getBookingId() == id) {
				if(bd.getBookingId() == id) {
					bookingRepo.save(bd);
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
