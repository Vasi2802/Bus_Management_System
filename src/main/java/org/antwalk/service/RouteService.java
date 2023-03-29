package org.antwalk.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.antwalk.entity.Route;
import org.antwalk.repository.RouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

	@Autowired
	private EntityManager entityManager;

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


	@Transactional
	public void deleteRouteByIdExternal(Long route_id) {
	    Route route = entityManager.find(Route.class,route_id);
	    if (route != null) {
	        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
	        entityManager.remove(route);
	        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
	    }
	   // return "routeRepo.deleteById(route_id)";
	}

	
}
