package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Route;
import org.antwalk.service.BusService;
import org.antwalk.service.RouteService;
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
@RequestMapping("/bus")
public class BusController {
	
	@Autowired
	BusService busService;

	@Autowired
	RouteService routeService;
	
	@PostMapping("/insert")
	public Bus insert(@RequestBody Bus b) {
		return busService.insertBus(b);
	}
	
	@GetMapping("/getall")
	public List<Bus> getAll(){
		return busService.getAllBus();
	}
	
	@GetMapping("/getbyid/{id}")
	public Bus getById(@PathVariable long id) {
		return busService.getBusById(id);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		busService.deleteBusById(id);
		return "Deleted";
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody Bus b, @PathVariable long id) {
		List<Bus> busList = busService.getAllBus();
		for(Bus obj:busList) {
			if(obj.getBid() == id) {
				if(b.getBid() == id) {
					busService.insertBus(b);
					return "Updated";
				}
				
				else {
					return "Id doesn't match";
				}
				
			}
		}
		return "Id does not exist";
		
	}

	@GetMapping("/getByRouteId/{routeId}")
	public List<Bus> getBusesByRouteId(@PathVariable Long routeId) {
		Route route = routeService.getRouteById(routeId);
		List<Bus> buses = busService.findAllByR(route);
		return buses;
	}

	// @GetMapping("/getByRouteId/{routeId}")
	// public List<Bus> getBusesByRouteId(@PathVariable Long routeId) {
	// 	List<Bus> buses = new ArrayList<>();
	// 	buses.add(new Bus(1, 20, 10, null, null, null));
	// 	buses.add(new Bus(1, 20, 0, null, null, null));
		
	// 	return buses;
	// }
	
}
