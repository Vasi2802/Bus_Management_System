package org.antwalk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antwalk.entity.Bus;
import org.antwalk.repository.BusRepo;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BusService {

	@Autowired
	private BusRepo busRepo;
	
	public Bus insertBus(Bus b) {
		return busRepo.save(b);
	}
	
	public List<Bus> getAllBus(){
		return busRepo.findAll();
	}
	
	public Bus getBusById(long id) {
		return busRepo.findById(id).get();
	}
	
	public String deleteBusById(long id) {
		busRepo.deleteById(id);
		return "Bus Deleted";
	}
	
	public String updateBusById(Bus b, long id) {
		List<Bus> busList = busRepo.findAll();
		for(Bus obj:busList) {
			if(obj.getBid() == id) {
				if(b.getBid() == id) {
					busRepo.save(b);
					return "Bus Updated";
				}
				
				else {
					return "Bus exists but your input id does not match with the existing Bus id";
				}
				
			}
		}
		return "Bus does not exist";
		
	}

	// GET TOTAL NO. OF PASSENGERS PER BUS
	/*
	 * Returns Map<Bus, PassengersCount> as Map<Bus, Integer>
	 * 
	 * Arguments
	 * None
	 */

	public Map<Bus, Integer> getPassesngersPerBus() {
		Map<Bus, Integer> passengerFreq = new HashMap<>();
		List<Bus> buses = busRepo.findAll();
		for(Bus bus:buses){
			passengerFreq.put(bus, bus.getTotalSeats()-bus.getAvailableSeats());
		}
		return passengerFreq;
	}

	public int getTotalSeatsOfAll(){
		List<Bus> buses = busRepo.findAll();
		int total = 0;
		for(Bus bus:buses){
			total += bus.getTotalSeats();
		}
		return total;
	}

	public int getTotalBookedSeats() {
		List<Bus> buses = busRepo.findAll();
		int booked = 0;
		for(Bus bus:buses){
			booked += bus.getTotalSeats() - bus.getAvailableSeats();
		}
		return booked;
	}



	
	
}
