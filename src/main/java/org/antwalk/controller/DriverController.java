package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.repository.DriverRepo;
import org.antwalk.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/driver")
public class DriverController {
	
	@Autowired
	DriverService driverService;
	
	@PostMapping("/insert")
	public Driver insert(@RequestBody Driver d) {
		return driverService.insertDriver(d);
	}
	
	@GetMapping("/getall")
	public List<Driver> getAll(){
		return driverService.getAllDrivers();
	}
	
	@GetMapping("/getbyid/{id}")
	public Driver getById(@PathVariable long id) {
		return driverService.getDriverById(id);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		driverService.deleteDriverById(id);
		return "Deleted";
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody Driver d, @PathVariable long id) {
		List<Driver> driverList = driverService.getAllDrivers();
		for(Driver obj:driverList) {
			if(obj.getDid() == id) {
				if(d.getDid() == id) {
					driverService.insertDriver(d);
					return "Updated";
				}
				
				else {
					return "Id doesn't match";
				}
				
			}
		}
		return "Id does not exist";
		
	}
	
	@GetMapping("/routedetails")
	public ModelAndView booking() {
		System.out.println("Working");
		ModelAndView modelAndView = new ModelAndView("route-details");
		return modelAndView;
	}
}
