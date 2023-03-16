package org.antwalk.service;

import java.util.List;

import org.antwalk.entity.Stop;
import org.antwalk.repository.StopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StopService {
	
	@Autowired
	private StopRepo stopRepo;
	
	public Stop insertStop(Stop s) {
		return stopRepo.save(s);
	}

	public List<Stop> getAllStops(){
		return stopRepo.findAll();
	}
	
	
	public Stop getStopById(long id) {
		return stopRepo.findById(id).get();
	}
	
	public Stop getStopByName(String name) {
		return stopRepo.findByName(name);
	}
	
	public String deleteStopById(long id) {
		stopRepo.deleteById(id);
		return "Stop Deleted";
	}
	
	public String updateStopById(Stop s, long id) {
		List<Stop> stopList = stopRepo.findAll();
		for(Stop obj:stopList) {
			if(obj.getSid() == id) {
				if(s.getSid() == id) {
					stopRepo.save(s);
					return "Stop Updated";
				}
				else {
					return "Stop exists but your input id does not match with the existing stop id";
				}
			}
		}
		return "Stop does not exist";
	}
	
	public String updateStopByName(Stop s, String name) {
		List<Stop> stopList = stopRepo.findAll();
		for(Stop obj:stopList) {
			if(obj.getName().equals(name)) {
//				if(s.getName().equals(name)) {
					stopRepo.save(s);
					return "Stop Updated";
//				}
//				else {
//					return "Stop exists but your input name does not match with the existing stop name";
//				}
			}
		}
		return "Stop does not exist";
	}
}
