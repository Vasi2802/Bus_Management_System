package org.antwalk.service;

import java.util.List;

import org.antwalk.entity.Driver;
import org.antwalk.repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DriverService {

	@Autowired
	private DriverRepo driverRepo;
	
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
}
