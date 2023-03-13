package org.antwalk.service;

import java.util.List;

import org.antwalk.entity.Route;
import org.antwalk.repository.RouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

	@Autowired
	private RouteRepo routeRepo;
	
	public Route insertRoute(Route r) {
		return routeRepo.save(r);
	}
	
	public List<Route> getAllRoutes(){
		return routeRepo.findAll();
	}
	
	public Route getRouteById(long id) {
		return routeRepo.findById(id).get();
	}
	
	public String deleteRouteById(long id) {
		routeRepo.deleteById(id);
		return "Route Deleted";
	}
	
	public String updateRouteById(Route r, long id) {
		List<Route> routeList = routeRepo.findAll();
		for(Route obj:routeList) {
			if(obj.getRid() == id) {
				if(r.getRid() == id) {
					routeRepo.save(r);
					return "Route Updated";
				}
				else {
					return "Route exists but your input id does not match with the existing route id";
				}
				
			}
		}
		return "Route does not exist";
	}
}
