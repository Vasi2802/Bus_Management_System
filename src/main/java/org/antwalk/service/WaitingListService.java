package org.antwalk.service;

import java.util.List;

import org.antwalk.entity.WaitingList;
import org.antwalk.repository.WaitingListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class WaitingListService {

	@Autowired
	private WaitingListRepo waitRepo;
	
	public WaitingList insertWaitingList(WaitingList wl) {
		return waitRepo.save(wl);
	}
	
	public List<WaitingList> getAllWaitingList(){
		return waitRepo.findAll();
	}
	
	public WaitingList getWaitingListById(long id) {
		return waitRepo.findById(id).get();
	}
	
	public String deleteWaitingListById(long id) {
		waitRepo.deleteById(id);
		return "Waiting List Deleted";
	}
	
	public String updateWaitingListById(WaitingList wl, long id) {
		List<WaitingList> waitList = waitRepo.findAll();
		for(WaitingList obj:waitList) {
			if(obj.getWid() == id) {
				if(wl.getWid() == id) {
					waitRepo.save(wl);
					return "Waiting List Updated";
				}
				
				else {
					return "Waiting List exists but your input id does not match with the existing Waiting List id";
				}
				
			}
		}
		return "Waiting List does not exist";
		
	}
}
