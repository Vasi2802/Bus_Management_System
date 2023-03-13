package org.antwalk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.antwalk.entity.Admin;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Route;
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
import org.springframework.http.ResponseEntity;
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
		ModelAndView modelAndView = new ModelAndView("addBusPage");
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
	public Map<String, Integer> passengerCoutntPerBus() {
		Map<Bus, Integer> busFreq = busService.getPassesngersPerBus();
		Map<String, Integer> busFreqWithDesc = new HashMap<>();
		for (Bus bus : busFreq.keySet()) {
			busFreqWithDesc.put(
					"BUS" + bus.getBid() + " on " + arrivalTimeService.getRouteDescription(bus.getR().getRid()),
					busFreq.get(bus));
		}
		return busFreqWithDesc;
	}

	// GET TOTAL BOOKING PER MONTH FOR CURRENT YEAR

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

		return statistics;
	}

	// GENERATE REPORT
	@GetMapping("/analytics/downlaod-report")
	public ResponseEntity<byte[]> downloadReport(){
		return adminService.generateReport();
	}


}
