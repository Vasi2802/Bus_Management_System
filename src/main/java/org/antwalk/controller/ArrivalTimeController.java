package org.antwalk.controller;

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
import org.antwalk.service.ArrivalTimeService;
import org.antwalk.service.BusService;
import org.antwalk.service.RouteService;
import org.antwalk.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arrivaltime")
public class ArrivalTimeController {

	@Autowired
	private ArrivalTimeService arrivalTimeService;

	@Autowired
	private StopService stopservice;

	@Autowired
	private BusService busService;

	@Autowired
	private RouteService routeService;

	@PostMapping("/insert")
	public ArrivalTimeTable insert(@RequestBody ArrivalTimeTable at) {
		return arrivalTimeService.insertArrivalTime(at);
	}

	@GetMapping("/getall")
	public List<ArrivalTimeTable> getAll() {
		return arrivalTimeService.getAllArrivalTimes();
	}

	@GetMapping("/getbyid/{rid}/{sid}")
	public ArrivalTimeTable getById(@PathVariable long rid, @PathVariable long sid) {
		return arrivalTimeService.getArrivalTimeById(rid, sid);
	}

	@DeleteMapping("/deletebyid/{rid}/{sid}")
	public String deleteById(@PathVariable long rid, @PathVariable long sid) {
		return arrivalTimeService.deleteArrivalTimeById(rid, sid);

	}

	@PutMapping("/update/{rid}/{sid}")
	public String update(@RequestBody ArrivalTimeTable at, @PathVariable long rid, @PathVariable long sid) {
		return arrivalTimeService.updateArrivalTimeById(at, rid, sid);
	}

	// ***************************************************************************
	// ******************* Rest APIs to serve business logic *********************
	// ***************************************************************************

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

	@GetMapping("/getallrouteswithtimebystopid")
	public List<List<ArrivalTimeTable>> getRoutesByStopId(@RequestParam long stopId, @RequestParam String shift) {
		try {
			Stop stop = stopservice.getStopById(stopId);
			Set<Route> routes = arrivalTimeService.findAllByRouteStopId_Stop(stop).stream()
					.map(ArrivalTimeTable::getRouteStopId).collect(Collectors.toList()).stream()
					.map(RouteStopId::getRoute)
					.collect(Collectors.toSet());
			List<List<ArrivalTimeTable>> routesWithTime = new ArrayList();
			for (Route route : routes) {
				List<ArrivalTimeTable> routeWithTime = arrivalTimeService
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
	@GetMapping("/getallstopsinaroute")
	public List<Stop> getStopsByRouteId(@RequestParam long routeId, @RequestParam String shift) {
		try {
			Route route = routeService.getRouteById(routeId);
			List<ArrivalTimeTable> arrivalTimeTables = null;
			if (shift.equalsIgnoreCase("morning")) {
				arrivalTimeTables = arrivalTimeService.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);
			} else {
				arrivalTimeTables = arrivalTimeService.findAllByRouteStopId_RouteOrderByEveningArrivalTime(route);
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
	@GetMapping("/getallstopswithtime")
	public List<ArrivalTimeTable> getAllStopsWithTimeByRouteId(@RequestParam long routeId, @RequestParam String shift) {
		try {
			Route route = routeService.getRouteById(routeId);
			if (shift.equalsIgnoreCase("morning")) {
				return arrivalTimeService.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);
			}

			else {
				return arrivalTimeService.findAllByRouteStopId_RouteOrderByEveningArrivalTime(route);
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
	@GetMapping("/viewroutesas2stops")
	public HashMap<Long, ArrivalTimeTable[]> getRoutesStartStop(@RequestParam long stopId, @RequestParam String shift) {

		try {
			Stop stop = stopservice.getStopById(stopId);
			Set<Route> routes = arrivalTimeService.findAllByRouteStopId_Stop(stop).stream()
					.map(ArrivalTimeTable::getRouteStopId).collect(Collectors.toList()).stream()
					.map(RouteStopId::getRoute)
					.collect(Collectors.toSet());
			HashMap<Long, ArrivalTimeTable[]> routeStartStop = new HashMap<Long, ArrivalTimeTable[]>();
			for (Route route : routes) {
				List<ArrivalTimeTable> routeWithTime = arrivalTimeService
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
	@GetMapping("/getallroutesasstopslist")
	public Map<Long, List<Stop>> getAllRoutesAsListOfStop() {
		Map<Long, List<Stop>> routes = new HashMap<>();
		for (Route route : routeService.getAllRoutes()) {
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
}
