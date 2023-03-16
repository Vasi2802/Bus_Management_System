package org.antwalk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.antwalk.repository.ArrivalTimeRepo;
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.StopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ArrivalTimeService {

	@Autowired
	private ArrivalTimeRepo arrivalTimeRepo;

	@Autowired
	private RouteRepo routeRepo;

	@Autowired
	private StopRepo stopRepo;

	public ArrivalTimeTable insertArrivalTime(ArrivalTimeTable at) {
		return arrivalTimeRepo.save(at);
	}

	public List<ArrivalTimeTable> getAllArrivalTimes() {
		return arrivalTimeRepo.findAll();
	}

	public ArrivalTimeTable getArrivalTimeById(long rid, long sid) {
		return arrivalTimeRepo.findById(new RouteStopId(routeRepo.findById(rid).get(), stopRepo.findById(sid).get()))
				.get();
	}

	public String deleteArrivalTimeById(long rid, long sid) {
		arrivalTimeRepo.deleteById(new RouteStopId(routeRepo.findById(rid).get(), stopRepo.findById(sid).get()));
		return "Arrival Time Deleted";
	}

	public String updateArrivalTimeById(ArrivalTimeTable at, long rid, long sid) {
		List<ArrivalTimeTable> atList = arrivalTimeRepo.findAll();
		for (ArrivalTimeTable obj : atList) {
			if (obj.getRouteStopId()
					.equals(new RouteStopId(routeRepo.findById(rid).get(), stopRepo.findById(sid).get()))) {
				if (at.getRouteStopId()
						.equals(new RouteStopId(routeRepo.findById(rid).get(), stopRepo.findById(sid).get()))) {
					arrivalTimeRepo.save(at);
					return "Arrival Time Updated";
				}

				else {
					return "Arrival Time exists but your input id does not match with the existing Arrival Time id";
				}

			}
		}
		return "Arrival Time does not exist";
	}

	// ###################################################################
	// GET ALL ROUTES WITH STOP ARRIVAL TIME
	/*
	 * ---------------------------------------------------------------------------
	 * returns List of routes as sorted list of ArrivalTimeTable i.e.,
	 * {StopId, RouteId, MorningTime, EveningTime}
	 * which pass through the stop with stopId sent in query parameter
	 * Stops are sorted by Morning arrival Time
	 * 
	 * PARAMETERS
	 * stopId
	 * shift (currently unused)
	 * ---------------------------------------------------------------------------
	 */

	public List<List<ArrivalTimeTable>> getRoutesByStopId(long stopId, String shift) {
		try {
			Stop stop = stopRepo.findById(stopId).get();
			Set<Route> routes = arrivalTimeRepo.findAllByRouteStopId_Stop(stop).stream()
					.map(ArrivalTimeTable::getRouteStopId).collect(Collectors.toList()).stream()
					.map(RouteStopId::getRoute)
					.collect(Collectors.toSet());
			List<List<ArrivalTimeTable>> routesWithTime = new ArrayList();
			for (Route route : routes) {
				List<ArrivalTimeTable> routeWithTime = arrivalTimeRepo
						.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);
				routesWithTime.add(routeWithTime);
			}
			return routesWithTime;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ArrayList();
		}
	}

	// Dummy function
	/*
	 * ================================ Dummy function
	 * ====================================
	 * 
	 * @GetMapping("/getallroutes/{stopId}")
	 * public List<Object> getRoutesByStopId(@PathVariable long stopId){
	 * 
	 * List<ArrivalTimeTable> arrivalTimeTables =
	 * arrivalTimeRepo.findAllByRouteStop_Stop(new Stop(1, "Stop1"));
	 * 
	 * List<Object> routes = new ArrayList();
	 * HashMap<String, Object> route = new HashMap<>();
	 * List<Stop> stops = new ArrayList(); // temporary list of stops. to be fetched
	 * stops.add(new Stop(1, "Stop1"));
	 * stops.add(new Stop(2, "Stop2"));
	 * stops.add(new Stop(3, "Stop3"));
	 * stops.add(new Stop(4, "Stop4"));
	 * stops.add(new Stop(5, "Stop5"));
	 * 
	 * route.put("route", new Route(1, stops.get(0), stops.get(3), "true"));
	 * route.put("stops", stops);
	 * 
	 * routes.add(route);
	 * routes.add(route.clone());
	 * return routes;
	 * }
	 */

	// ###################################################################
	// GET ALL STOPS IN A ROUTE
	/*
	 * ---------------------------------------------------------------------------
	 * returns a sorted List of Stops in a route
	 * which pass through the stop with stopId sent in query parameter
	 * Stops are sorted by shift (parameter)
	 * 
	 * PARAMETERS
	 * routeId
	 * shift
	 * ---------------------------------------------------------------------------
	 */
	public List<Stop> getStopsByRouteId(long routeId, String shift) {
		try {
			Route route = routeRepo.findById(routeId).get();
			List<ArrivalTimeTable> arrivalTimeTables = null;
			if (shift.equalsIgnoreCase("morning")) {
				arrivalTimeTables = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);
			} else {
				arrivalTimeTables = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByEveningArrivalTime(route);
			}
			List<Stop> stops = arrivalTimeTables.stream().map(ArrivalTimeTable::getRouteStopId)
					.collect(Collectors.toList()).stream().map(RouteStopId::getStop).collect(Collectors.toList());

			return stops;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	// DUMMY FUNCTION
	/*
	 * ================================ Dummy function
	 * ====================================
	 * 
	 * @GetMapping("/getallstopsinaroute")
	 * public List<Stop> getStopsByRouteId(@RequestParam long routeId, @RequestParam
	 * String shift){
	 * List<Stop> stops = new ArrayList(); // temporary list of stops. to be fetched
	 * stops.add(new Stop(1, "Stop1"));
	 * stops.add(new Stop(2, "Stop2"));
	 * stops.add(new Stop(3, "Stop3"));
	 * stops.add(new Stop(4, "Stop4"));
	 * stops.add(new Stop(5, "Stop5"));
	 * return stops;
	 * }
	 */

	// ###################################################################
	// GET ALL STOPS WITH TIME IN A ROUTE
	/*
	 * ---------------------------------------------------------------------------
	 * returns a sorted List of ArrivalTimeTable ie.
	 * {RouteStop, EveningArrivalTime, MorningArrivalTime} in a route
	 * Stops are sorted by shift (parameter)
	 * 
	 * PARAMETERS
	 * routeId
	 * shift
	 * ---------------------------------------------------------------------------
	 */
	public List<ArrivalTimeTable> getAllStopsWithTimeByRouteId(long routeId, String shift) {
		try {
			Route route = routeRepo.findById(routeId).get();
			if (shift.equalsIgnoreCase("morning")) {
				return arrivalTimeRepo.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);
			}

			else {
				return arrivalTimeRepo.findAllByRouteStopId_RouteOrderByEveningArrivalTime(route);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ArrayList();
		}

	}

	// ###################################################################
	// GET ALL ROUTES AS PICK_UP_STOP and DROP_OFF_STOP WITH TIME
	/*
	 * ---------------------------------------------------------------------------
	 * returns a Map = {RouteId->[PickUpStop,DropOffStop]} with time
	 * for a given pickup Stop
	 * DropOffStop is the Office Location
	 * 
	 * PARAMETERS
	 * stopId (the PickUpStop)
	 * shift
	 * ---------------------------------------------------------------------------
	 */
	public HashMap<Long, ArrivalTimeTable[]> getRoutesStartStop(long stopId, String shift) {

		try {
			Stop stop = stopRepo.findById(stopId).get();
			Set<Route> routes = arrivalTimeRepo.findAllByRouteStopId_Stop(stop).stream()
					.map(ArrivalTimeTable::getRouteStopId).collect(Collectors.toList()).stream()
					.map(RouteStopId::getRoute)
					.collect(Collectors.toSet());
			HashMap<Long, ArrivalTimeTable[]> routeStartStop = new HashMap<Long, ArrivalTimeTable[]>();
			for (Route route : routes) {
				List<ArrivalTimeTable> routeWithTime = arrivalTimeRepo
						.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);

				for (ArrivalTimeTable stopWithTime : routeWithTime) {
					if (stopWithTime.getRouteStopId().getStop().getSid() == stopId) {
						routeStartStop.put(route.getRid(), new ArrivalTimeTable[] { stopWithTime,
								routeWithTime.get(routeWithTime.size() - 1) });
						break;
					}
				}
			}
			return routeStartStop;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new HashMap();
		}
	}

	// ###########################################################################
	// GET ALL ROUTES AS List<Stop> mapped by routeId
	/*
	 * ---------------------------------------------------------------------------
	 * returns a Map<Long, List<Stop>> where each List<Stop> represents a route
	 * List<Stop> is sorted by morning time
	 * ---------------------------------------------------------------------------
	 */
	public Map<Long, List<Stop>> getAllRoutesAsListOfStop() {
		Map<Long, List<Stop>> routes = new HashMap<>();
		for (Route route : routeRepo.findAll()) {
			List<Stop> stops = getStopsByRouteId(route.getRid(), "morning");
			routes.put(route.getRid(), stops);
			System.out.println(route.getRid() + "" + stops);
		}
		return routes;
	}

	// DUMMY
	/*
	 * Route route = new Route(1, null, null, "Morning");
	 * ArrivalTimeTable start = new ArrivalTimeTable(new RouteStop(),
	 * LocalTime.of(8, 0), LocalTime.of(18, 0));
	 * ArrivalTimeTable end = new ArrivalTimeTable(new RouteStop(), LocalTime.of(9,
	 * 0), LocalTime.of(17, 0));
	 * ArrivalTimeTable[] pickupDrop = new ArrivalTimeTable[]{start,end};
	 * Route route1 = new Route(2, null, null, "Morning");
	 * ArrivalTimeTable start1 = new ArrivalTimeTable(new RouteStop(),
	 * LocalTime.of(7, 0), LocalTime.of(19, 0));
	 * ArrivalTimeTable end1 = new ArrivalTimeTable(new RouteStop(), LocalTime.of(9,
	 * 0), LocalTime.of(17, 0));
	 * ArrivalTimeTable[] pickupDrop1 = new ArrivalTimeTable[]{start,end};
	 * HashMap<Long, ArrivalTimeTable[]> routeStartStop = new HashMap<Long,
	 * ArrivalTimeTable[]>();
	 * routeStartStop.put(route1.getRid(), pickupDrop1);
	 * routeStartStop.put(route.getRid(), pickupDrop);
	 * return routeStartStop;
	 */

	// GET ROUTE DESCRIPTION AS "ROUTEID: START_STOP TO END_STOP"
	public String getRouteDescription(long routeId) {
		List<Stop> stops = getStopsByRouteId(routeId, "morning");
		return "RouteID-" + routeId + " " +
				stops.get(0).getName() +
				" to " + stops.get(stops.size() - 1).getName();
	}
}
