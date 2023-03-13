package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.repository.DriverRepo;
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
@RequestMapping("/driver")
public class DriverController {
	
	@Autowired
	DriverRepo driverRepo;
	
	@PostMapping("/insert")
	public Driver insert(@RequestBody Driver d) {
		return driverRepo.save(d);
	}
	
	@GetMapping("/getall")
	public List<Driver> getAll(){
		return driverRepo.findAll();
	}
	
	@GetMapping("/getbyid/{id}")
	public Driver getById(@PathVariable long id) {
		return driverRepo.findById(id).get();
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		driverRepo.deleteById(id);
		return "Deleted";
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody Driver d, @PathVariable long id) {
		List<Driver> driverList = driverRepo.findAll();
		for(Driver obj:driverList) {
			if(obj.getDid() == id) {
				if(d.getDid() == id) {
					driverRepo.save(d);
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
