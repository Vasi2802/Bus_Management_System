package org.antwalk.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DriverService {

	@Autowired
	private DriverRepo driverRepo;

	@Autowired
	private BookingDetailsService bookingDetailsService;

	@Autowired
	private ArrivalTimeService arrivalTimeService;
	
	public Driver insertDriver(Driver d) {
		return driverRepo.save(d);
	}
	
	public List<Driver> getAllDrivers(){
		return driverRepo.findAll();
	}
	
	public Driver getDriverById(long id) {
		return driverRepo.findById(id).get();
	}
	
	public String deleteDriverById(long id) {
		driverRepo.deleteById(id);
		return "Driver Deleted";
	}
	
	public String updateDriverById(Driver d, long id) {
		List<Driver> driverList = driverRepo.findAll();
		for(Driver obj:driverList) {
			if(obj.getDid() == id) {
				if(d.getDid() == id) {
					driverRepo.save(d);
					return "Driver Updated";
				}
				
				else {
					return "Driver exists but your input id does not match with the existing Driver id";
				}
				
			}
		}
		return "Driver does not exist";
		
	}

	public List<HashMap<String,Object>> getAllPassengers(Driver driver){
		Bus bus = driver.getBus();
		Route route = bus.getR();
		LocalTime curTime = LocalTime.now();
		String shift = curTime.getHour()>14?"morning":"evening"; // shift is evening if current time is past 2pm
		
		List<BookingDetails> bookingDetailsList = bookingDetailsService.findAllByB(bus);
		List<ArrivalTimeTable> arrivalTimeTables = arrivalTimeService.getAllStopsWithTimeByRouteId(route.getRid(), shift);
		List<HashMap<String,Object>> passengers = new ArrayList<>();
		for(ArrivalTimeTable arrivalTimeTable: arrivalTimeTables){
			HashMap<String,Object> stopMap = new HashMap<>();
			List<Object> employees = new ArrayList<>();
			Stop stop = arrivalTimeTable.getRouteStopId().getStop();
			stopMap.put("stop", stop);
			for(BookingDetails bookingDetails: bookingDetailsList){
				if(bookingDetails.getStop().getSid()==stop.getSid()){
					HashMap<String, String> employee = new HashMap<>();
					employee.put("name",bookingDetails.getE().getName());
					employee.put("eid", "" + bookingDetails.getE().getEid());
					employee.put("contactNo",bookingDetails.getE().getContactNo());
					employee.put("boardingStatus","" + bookingDetails.getIsBoarded());
					employees.add(employee);
				}
			}
			stopMap.put("employees", employees);
			if(employees.size()>0){
				passengers.add(stopMap);
			}
		}
		return passengers;
	}
	
	// call during end journey
	public void clearBoardingStatus(){
		List<BookingDetails> bookingDetailsList = bookingDetailsService.getAllBookingDetails();
		for(BookingDetails bookingDetails : bookingDetailsList){
			bookingDetails.setIsBoarded(false);
			bookingDetailsService.insertBookingDetails(bookingDetails);
		}
	}
}
