package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.Route;
import org.antwalk.repository.RouteRepo;
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
@RequestMapping("/route")
public class RouteController {
	
	@Autowired
	RouteRepo routeRepo;
	
	@PostMapping("/insert")
	public Route insert(@RequestBody Route r) {
		return routeRepo.save(r);
	}
	
	@GetMapping("/getall")
	public List<Route> getAll(){
		return routeRepo.findAll();
	}
	
	@GetMapping("/getbyid/{id}")
	public Route getById(@PathVariable long id) {
		return routeRepo.findById(id).get();
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		routeRepo.deleteById(id);
		return "Deleted";
		
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody Route r, @PathVariable long id) {
		List<Route> routeList = routeRepo.findAll();
		for(Route obj:routeList) {
			if(obj.getRid() == id) {
				if(r.getRid() == id) {
					routeRepo.save(r);
					return "Updated";
				}
				
				else {
					return "Id doesn't match";
				}
				
			}
		}
		return "Id does not exist";

		/* Rest APIs to serve business logic */

		
	}
}
