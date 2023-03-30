package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.Stop;
import org.antwalk.service.StopService;
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
@RequestMapping("/stop")
public class StopController {
	
	@Autowired
	StopService stopService;
	
	@PostMapping("/insert")
	public Stop insert(@RequestBody Stop s) {
		return stopService.insertStop(s);
	}
	
	@GetMapping("/getall")
	public List<Stop> getAll(){
		return stopService.getAllStops();
	}
	
	@GetMapping("/getbyid/{id}")
	public Stop getOne(@PathVariable long id) {
		return stopService.getStopById(id);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		stopService.deleteStopById(id);
		return "Deleted";
		
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody Stop s, @PathVariable long id) {
		List<Stop> stopList = stopService.getAllStops();
		for(Stop obj:stopList) {
			if(obj.getSid() == id) {
				if(s.getSid() == id) {
					stopService.insertStop(s);
					return "Updated";
				}
				
				else {
					return "Id doesn't match";
				}
				
			}
		}
		return "Id does not exist";
		
	}

	@PutMapping("/update")
	public Stop update(@RequestBody Stop stop) {
	
		stopService.insertStop(stop);
		return stop;
		
	}

	@GetMapping(value="/stopform")
	public ModelAndView postMethodName() {
		ModelAndView modelAndView = new ModelAndView("stopForm");		
		return modelAndView;
	}


	


	
}
