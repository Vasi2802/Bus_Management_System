package org.antwalk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.antwalk.entity.Admin;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Route;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.AdminRepo;
import org.antwalk.repository.ArrivalTimeRepo;
import org.antwalk.repository.BookingDetailsRepo;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.DriverRepo;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.StopRepo;
import org.antwalk.repository.UserRepo;
import org.antwalk.repository.WaitingListRepo;
import org.antwalk.service.AdminService;
import org.antwalk.service.ArrivalTimeService;
import org.antwalk.service.BookingDetailsService;
import org.antwalk.service.BusService;
import org.antwalk.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.antwalk.service.ArrivalTimeService;
import org.antwalk.service.BookingDetailsService;
import org.antwalk.service.BusService;
import org.antwalk.service.DriverService;
import org.antwalk.service.EmployeeService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.antwalk.user.CrmUser;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.antwalk.entity.ArrivalTimeTable;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private EmployeeRepo empRepo;

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private StopRepo stopRepo;

	@Autowired
	private BookingDetailsRepo bookingDetailsRepo;

	@Autowired
	private RouteRepo routeRepo;

	@Autowired
	private DriverRepo driverRepo;

	@Autowired
	private WaitingListRepo waitingListRepo;

	@Autowired
	private ArrivalTimeRepo arrivalTimeRepo;

	@Autowired
	private ArrivalTimeService arrivalTimeService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private BusService busService;

	@Autowired
	private BookingDetailsService bookingDetailsService;

	@Autowired
	private AdminService adminService;
	private RouteService routeService;
	

	@Autowired
	private DriverService driverService;


	@PostMapping("/insert")
	public Admin insert(@RequestBody Admin a) {
		return adminRepo.save(a);
	}

	@GetMapping("/getall")
	public List<Admin> getAll() {
		return adminRepo.findAll();
	}

	@GetMapping("/getbyid/{id}")
	public Admin getById(@PathVariable long id) {
		return adminRepo.findById(id).get();
	}

	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		adminRepo.deleteById(id);
		return "Deleted";
	}

	@PutMapping("/update/{id}")
	public String update(@RequestBody Admin a, @PathVariable long id) {
		List<Admin> adminList = adminRepo.findAll();
		for (Admin obj : adminList) {
			if (obj.getAid() == id) {
				if (a.getAid() == id) {
					adminRepo.save(a);
					return "Updated";
				} else {
					return "Id doesn't match";
				}
			}
		}
		return "Id does not exist";
	}

	@GetMapping("addbus")
	public ModelAndView addBusPage() {
		ModelAndView modelAndView = new ModelAndView("addBus");
		return modelAndView;
	}


	@GetMapping("/analytics")
	public ModelAndView getAllWaitingAnalytics() {
		ModelAndView modelAndView = new ModelAndView("analytics");
		return modelAndView;
	}

	@GetMapping("/get-all-drivers")
	public List<Driver> getAllDrivers() {
		List<Driver> drivers = driverRepo.findAll();
		return drivers;
	}

	@GetMapping("/get-available-drivers")
	public List<Driver> getAllAvailableDrivers() {
		List<Driver> drivers = driverRepo.findAll();
		List<Driver> availableDrivers = new ArrayList<>();
		for (Driver driver : drivers) {
			if (busRepo.findByD(driver).isEmpty())
				availableDrivers.add(driver);
		}
		return availableDrivers;
	}

	// ------------------------ Analytics --------------------------------------

	// GET ALL WAITLIST BY ROUTE ID

	// Analytics

	// GET ALL WAITLIST BY ROUTE ID
	@GetMapping("/analytics/waiting-by-routeid/{routeId}")
	public List<WaitingList> getWaitingListByRoute(@PathVariable Long routeId) {
		Route route = routeRepo.findById(routeId).get();
		List<Bus> buses = busRepo.findAllByR(route);
		List<WaitingList> waitingLists = waitingListRepo.findByBIn(buses);
		return waitingLists;
	}

	// GET COUNT OF WAITLIST BY ROUTEID

	@GetMapping("/analytics/count-waiting-by-routeid/{routeId}")
	public long getCountWaitingListByRoute(@PathVariable Long routeId) {
		Route route = routeRepo.findById(routeId).get();
		List<Bus> buses = busRepo.findAllByR(route);
		return waitingListRepo.countByBus(buses);
	}

	// GET TOTAL COUNT WAITLIST

	@GetMapping("/analytics/total-count-waiting")
	public long getTotalCountWaitingList() {
		return waitingListRepo.count();
	}

	// GET COUNT FOR ALL ROUTES IN WAITLIST

	@GetMapping("/analytics/count-waiting-each-route")
	public Map<String, Long> getCountAllWaitingList() {
		List<WaitingList> waitingLists = waitingListRepo.findAll();
		List<Route> routes = routeRepo.findAll();
		Map<Route, Long> routeCount = new HashMap<>();
		for (Route route : routes) {
			long freq = getCountWaitingListByRoute(route.getRid());
			routeCount.put(route, freq);
		}
		routeCount.entrySet()
				.stream()
				.filter(entry -> entry.getValue() > 0)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		Map<String, Long> routeDescCount = new HashMap<>();
		for (Route route : routeCount.keySet()) {
			List<Stop> stops = arrivalTimeService.getStopsByRouteId(route.getRid(), "morning");
			String routeDesc = arrivalTimeService.getRouteDescription(route.getRid());
			routeDescCount.put(routeDesc, routeCount.getOrDefault(route, 0L));
		}
		return routeDescCount;
	}

	// PASSENGER COUNT FOR EACH BUS

	@GetMapping("/analytics/passenger-count-per-bus")
	public Map<String, Integer> passengerCountPerBus() {
		Map<Bus, Integer> busFreq = busService.getPassesngersPerBus();
		Map<String, Integer> busFreqWithDesc = new HashMap<>();
		for (Bus bus : busFreq.keySet()) {

			busFreqWithDesc.put("BUS"+ bus.getBid() + " on " + arrivalTimeService.getRouteDescription(bus.getR().getRid()),
					busFreq.get(bus));
		}
		return busFreqWithDesc;
	}

	@GetMapping("/analytics/booking-per-month")
	public List<List<Object>> bookingPerMonth() {
		return bookingDetailsService.getBookingPerMonth();
	}
	// GET NUMBER STATISTICS
	/*
	 * TOTAL EMPLOYEES
	 * TOTAL BUSES
	 * TOTAL BOOKED SEATS
	 * TOTAL SEATS IN ALL BUSES
	 */

	@GetMapping("/analytics/get-stats")
	public Map<String, Integer> getTotalEmployees() {
		Map<String, Integer> statistics = new HashMap<>();
		statistics.put("totalEmployees", employeeService.getAllEmployees().size());
		statistics.put("totalBuses", busService.getAllBus().size());
		statistics.put("totalSeats", busService.getTotalSeatsOfAll());
		statistics.put("totalSeatsBooked", busService.getTotalBookedSeats());

		statistics.put("mostWaitlistedRoute", adminService.getMostWaitlistedRoute());
		statistics.put("mostWaitlistedBus", adminService.getMostWaitlistedBus());
		statistics.put("totalInWaitlist", adminService.getTotalInWaitlist());
		
		statistics.put("totalPassengers", busService.getTotalBookedSeats());
		statistics.put("mostUsedBus", (int)busService.getMostUsedBus());
		statistics.put("mostUsedRoute", (int)busService.getMostUsedRoute());

		return statistics;
	}

	// GENERATE REPORT
	@GetMapping("/analytics/downlaod-report")
	public ResponseEntity<Resource> downloadReport() {
		return adminService.generateReport();
	}

	// DELETE EMPLOYEE
	/*
	 * Deletes associated-
	 * booking details
	 * waitinglist
	 * user
	 */
	@GetMapping("/delete/employee/{employeeId}")
	public String deleteEmployee(@PathVariable long employeeId) {
		return adminService.deleteEmployee(employeeId);
	}

	// DELETE BUS
	/*
	 * Deletes associated - 
	 * 1. BookingDeetails
	 * 2. waitingList
	 * 3. busId of employees
	 */
	@GetMapping("/delete/bus/{busId}")
	public String deleteBus(@PathVariable long busId) {
		return adminService.deleteBus(busId);
	}

	// DELETE ROUTE
	/*
	 * Deletes associated -
	 * buses
	 * arrival timetable
	 */
	@GetMapping("/delete/route/{routeId}")
	public String deleteRoute(@PathVariable long routeId) {
		return adminService.deleteRoute(routeId);
	}
	
	@GetMapping("/")
	public ModelAndView getDashboard() {
		ModelAndView modelAndView = new ModelAndView("admin");
		return modelAndView;
	}

	@GetMapping("/manageBus")
	public ModelAndView manageBus() {
		ModelAndView modelAndView = new ModelAndView("manageBus");
		List<Bus> result = busService.getAllBus();
		modelAndView.addObject("list",result);
		return modelAndView;
	}

	@GetMapping("/manageDriver")
	public ModelAndView manageDriver() {
		String uri = "http://localhost:8080/admin/driver/getall";
		RestTemplate restTemplate = new RestTemplate();
		List<Driver> drivers = driverRepo.findAll();
		ModelAndView modelAndView = new ModelAndView("manageDriver");
		modelAndView.addObject("drivers", drivers);
		return modelAndView;
	}


	@GetMapping("/manageRoute")
	public ModelAndView manageRoute() {
		ModelAndView modelAndView = new ModelAndView("manageRoute");
		List<Route> routes = routeRepo.findAll();
		modelAndView.addObject("routes", routes);
		return modelAndView;
	}

	@GetMapping("/manageStop")
	public ModelAndView manageStop() {
		ModelAndView modelAndView = new ModelAndView("manageStop");
		return modelAndView;
	}

	@GetMapping("/addStop")
	public ModelAndView addStop() {
		ModelAndView modelAndView = new ModelAndView("addStop");
		return modelAndView;
	}


	@GetMapping("/addRoute")
	public ModelAndView addRoute() {
		ModelAndView modelAndView = new ModelAndView("addRoute");
		return modelAndView;
	}

	

	@PostMapping("/stop/insert")
	public Stop insert(@RequestBody Stop s) {
		return stopRepo.save(s);
	}

	@GetMapping("/editStop")
	public ModelAndView editStop() {
		String uri = "http://localhost:8080/stop/getall"; 
		RestTemplate restTemplate = new RestTemplate();
	    List<Stop> result = stopRepo.findAll();
		ModelAndView mv=new ModelAndView("editstop");
		mv.addObject("list", result);
		return mv;
	}


	@PutMapping("/stop/update")
	public Stop updateStop(@RequestBody Stop stop) {
		stopRepo.save(stop);
		return stop;
		
	}

	@GetMapping("/stop/getall")
	public List<Stop> getAllStops(){
		return stopRepo.findAll();
	}


	@PostMapping("/route/insert")
	public Route insertRoute(@RequestBody Route r) {
		return routeRepo.save(r);
	}


	@PostMapping("/arrivaltime/insert")
	public ArrivalTimeTable insert(@RequestBody ArrivalTimeTable at) {
		return arrivalTimeService.insertArrivalTime(at);
	}


	@GetMapping("/route/getall")
	public List<Route> getAllRoutes(){
		return routeRepo.findAll();
	}

	@RequestMapping("/viewRoutes")
	public ModelAndView viewRoutes(HttpServletRequest request) {
		String uri = "http://localhost:8080/admin/route/getall";
		RestTemplate restTemplate = new RestTemplate();
		List<Route> result = routeRepo.findAll();
		ModelAndView mv = new ModelAndView("viewRoutes");
		mv.addObject("list", result);
		return mv;

	}

	@GetMapping("/viewStops")
	public ModelAndView getAllStopsWithTimeByRouteid(@RequestParam Long rid, @RequestParam String shift) {
		try {
			Route route = routeRepo.findById(rid).get();
			List<ArrivalTimeTable> arrivalTimeTableList;
			if (shift.equalsIgnoreCase("morning")) {
				arrivalTimeTableList = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);
			} else {
				arrivalTimeTableList = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByEveningArrivalTime(route);
			}
			ModelAndView modelAndView = new ModelAndView("viewStops");
			// System.out.println(arrivalTimeTableList);
			modelAndView.addObject("arrivalTimeTableList", arrivalTimeTableList);
			return modelAndView;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error");
		}
	}

	@RequestMapping("/viewDrivers")
	public ModelAndView viewDrivers(HttpServletRequest request) {
		String uri = "http://localhost:8080/admin/driver/getall";
		RestTemplate restTemplate = new RestTemplate();
		List<Driver> result = driverRepo.findAll();
		System.out.println(result);
		ModelAndView mv = new ModelAndView("viewDrivers");
		mv.addObject("list", result);
		return mv;

	}

	@RequestMapping("/deleteRoute")
	public ModelAndView deleteRoute1(HttpServletRequest request) {
//		String uri = "http://localhost:8080/admin/route/getall";
//		RestTemplate restTemplate = new RestTemplate();
		List<Route> result = routeRepo.findAll();
		ModelAndView mv =new ModelAndView("deleteRoute");
//		// if(result.size() == 0){
//		// 	mv = new ModelAndView("alertPage");
//		// 	mv.addObject("message","no routes found!");
//		// 	return manageRoute();
//		// }
//		// else{
//		mv = new ModelAndView("deleteRoute");
		mv.addObject("list", result);
//		// }
//		return mv;
		
		return mv;
	}

	public String alertAndRedirect(String msg, String url) {
		return "<script>alert("+msg+")</script>";
	}

	@GetMapping("/alert")
	public ModelAndView alert() {
		ModelAndView mv = new ModelAndView("alert");
		return mv;
	}



	@RequestMapping("/manageEmployee")
	public ModelAndView manageEmployee() {
		ModelAndView mv = new ModelAndView("manageEmployee");
		// String uri = "http://localhost:8080/admin/emp/getall"; 
		// RestTemplate restTemplate = new RestTemplate();
	    // String result = restTemplate.getForObject(uri, String.class); 
	    List<Employee> employees = employeeService.getAllEmployees();
		mv.addObject("employees",employees);
		return mv;
	}

	@GetMapping("/employee/getbyid/{id}")
	public Employee getById1(@PathVariable long id) {
		return empRepo.findById(id).get();
	}

	@GetMapping("/route/deletebyid/{id}")
	 public ModelAndView deletebyid(@PathVariable("id")long id){
		routeService.deleteRouteByIdExternal(id);
		 String uri ="http://localhost:8080/admin/route/getall";
		 RestTemplate restTemplate = new RestTemplate();
		 List<Route> result =routeRepo.findAll();
		 ModelAndView mv = new ModelAndView("deleteRoute"); 
		 mv.addObject("list", result);
		 return mv;
	 }


	 @GetMapping("/emp/getall")
	public List<Employee> getAllEmployee() {
		return employeeService.getAllEmployees();
	}
	 
	 @GetMapping("/driver/getall")
		public List<Driver> getAllDriver() {
			return driverService.getAllDrivers();
		}

	 @GetMapping("/employee/deletebyidnew/{id}")
	public ModelAndView deleteByIdEmployee(@PathVariable("id") long id) {
		employeeService.deleteemployee(id);
		
		
		return manageEmployee();
	}

	@GetMapping("/editDriver")
	public ModelAndView editDriver() {
		String uri = "http://localhost:8080/admin/driver/getall";
		RestTemplate restTemplate = new RestTemplate();
		List<Driver> result = driverService.getAllDrivers();
		ModelAndView mv = new ModelAndView("editDriver");
		mv.addObject("list", result);
		return mv;
	}
	

	// @PutMapping("/driver/update/{id}")
	// public String updateDriver(@RequestBody Driver d, @PathVariable long id) {
	// 	List<Driver> driverList = driverService.getAllDrivers();
	// 	for(Driver obj:driverList) {
	// 		if(obj.getDid() == id) {
	// 			if(d.getDid() == id) {
	// 				driverService.insertDriver(d);
	// 				return "Updated";
	// 			}
				
	// 			else {
	// 				return "Id doesn't match";
	// 			}
				
	// 		}
	// 	}
	// 	return "Id does not exist";
		
	// }


	@PutMapping("/driver/update")
	public Driver updateDriver(@RequestBody Driver d) {
		// System.out.println(driverService.updateDriverById(d, id));
		return driverService.insertDriver(d);	
	}

	// @GetMapping("/addBus")
	// public ModelAndView AddBus(){
	// 	ModelAndView mv = new ModelAndView("addBus");
	// 	return mv;
	// }

	@GetMapping("/getallroutesasstopslist")
	public Map<Long, List<Stop>> getAllRoutesAsListOfStop() {
		Map<Long, List<Stop>> routes = new HashMap<>();
		for (Route route : routeRepo.findAll()) {
			List<Stop> stops = getStopsByRouteId(route.getRid(), "morning");
			routes.put(route.getRid(), stops);
			System.out.println(route.getRid() + "" + stops);
		}
		return routes;
	}

	@GetMapping("/getallstopsinaroute")
	public List<Stop> getStopsByRouteId(@RequestParam long routeId, @RequestParam String shift) {
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

	@GetMapping("/bus/insert")
	public Bus insert(@RequestParam int totalSeats, @RequestParam long routeId, @RequestParam long driverId) {
		return busService.addBus(totalSeats, routeId, driverId);
	}

	@GetMapping("/route/getbyid/{id}")
	public Route getByIdRoute(@PathVariable long id) {
		return routeRepo.findById(id).get();
	}


	@GetMapping("/driver/getbyid/{id}")
	public Driver getByIdDriver(@PathVariable long id) {
		return driverService.getDriverById(id);
	}


	@RequestMapping("/deleteBus")
	public ModelAndView deleteBus(){
	    List<Bus> result = busService.getAllBus();
		ModelAndView mv = new ModelAndView("deleteBus");
		mv.addObject("list",result);
		return mv;
	}

	@GetMapping("/bus/getall")
	public List<Bus> getAllBus(){
		return busRepo.findAll();
	}


	@GetMapping("/bus/deletebyidnew/{id}")
	public ModelAndView deleteById2(@PathVariable("id") long id) {
		busService.deleteBusById(id);
		return manageBus();
	}
	
}
