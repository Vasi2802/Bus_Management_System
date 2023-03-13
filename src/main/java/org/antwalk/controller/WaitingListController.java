package org.antwalk.controller;

import java.util.List;

import org.antwalk.entity.Driver;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.WaitingListRepo;
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
@RequestMapping("/waiting_list")
public class WaitingListController {

	@Autowired
	WaitingListRepo waitRepo;
	
	@PostMapping("/insert")
	public WaitingList insert(@RequestBody WaitingList wl) {
		return waitRepo.save(wl);
	}
	
	@GetMapping("/getall")
	public List<WaitingList> getAll(){
		return waitRepo.findAll();
	}
	
	@GetMapping("/getbyid/{id}")
	public WaitingList getById(@PathVariable long id) {
		return waitRepo.findById(id).get();
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		waitRepo.deleteById(id);
		return "Deleted";
	}
	
	@PutMapping("/update/{id}")
	public String update(@RequestBody WaitingList wl, @PathVariable long id) {
		List<WaitingList> waitList = waitRepo.findAll();
		for(WaitingList obj:waitList) {
			if(obj.getWid() == id) {
				if(wl.getWid() == id) {
					waitRepo.save(wl);
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
