package org.antwalk.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.History;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.ArrivalTimeRepo;
import org.antwalk.repository.BookingDetailsRepo;
import org.antwalk.repository.UserRepo;
import org.antwalk.repository.WaitingListRepo;
import org.antwalk.service.ArrivalTimeService;
import org.antwalk.service.BookingDetailsService;
import org.antwalk.service.BusService;
import org.antwalk.service.EmployeeService;
import org.antwalk.service.HistoryService;
import org.antwalk.service.RouteService;
import org.antwalk.service.StopService;
import org.antwalk.service.UserService;
import org.antwalk.service.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


	@Autowired
	ArrivalTimeService timeserv;

	@Autowired
	BusService busService;

	@Autowired
	UserRepo userRepo;

	@Autowired
	StopService stopService;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Autowired
	EmployeeService employeeService;

	@Autowired
	WaitingListService waitingListService;

	@Autowired
	BookingDetailsService bookingDetailsService;

	@Autowired
	HistoryService historyService;

	@Autowired
	RouteService routeService;

	@Autowired
	ArrivalTimeService arrivalTimeService;


	@PostMapping("/insert")
	public Employee insert(@RequestBody Employee e) {
		return employeeService.insertEmployee(e);
	}

	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		employeeService.deleteemployee(id);
		return "Deleted";
	}

	@PutMapping("/update/{id}")
	public String update(@RequestBody Employee e, @PathVariable long id) {
		List<Employee> empList = employeeService.getAllEmployees();
		for (Employee obj : empList) {
			if (obj.getEid() == id) {
				if (e.getEid() == id) {
					employeeService.insertEmployee(e);
					return "Updated";
				}

				else {
					return "Id doesn't match";
				}

			}
		}
		return "Id does not exist";

	}

	/*
	 * @GetMapping("/") public String hello(){ return "Hello"; }
	 */

	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView modelAndView = new ModelAndView("employee");
		return modelAndView;
	}

	@GetMapping("/releaseseat")
	public ModelAndView releaseseat(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("release-seat-form");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("emp");
		Employee employee = employeeService.getEmployeeById(user.getEmployee().getEid());
		Optional<WaitingList> waitingListOptional = waitingListService.findByE(employee);
		if (employee.getB() == null && waitingListOptional.isEmpty()) {
			modelAndView = new ModelAndView("error-seat-form");
			return modelAndView;
		}

		return modelAndView;
	}

	@GetMapping("/showbookingdetails")
	public ModelAndView booking(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("employee-booking-details");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("emp");
		Employee employee = employeeService.getEmployeeById(user.getEmployee().getEid());
		if (employee.getB() == null) {
			modelAndView = new ModelAndView("error-booking-details");
			return modelAndView;
		}
		return modelAndView;
	}

	@GetMapping("/trackbus")
	public ModelAndView trackbus(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("employee-track-bus");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("emp");
		Employee employee = employeeService.getEmployeeById(user.getEmployee().getEid());
		if (employee.getB() == null) {
			modelAndView = new ModelAndView("error-track-bus");
			return modelAndView;
		}

		Long rid = employee.getB().getR().getRid();

		LocalTime currentTime = LocalTime.now(); // get the current time
		LocalTime noon = LocalTime.of(12, 0); // set noon time to 12:00 PM

		if (currentTime.isBefore(noon)) {
			List<Stop> stops = timeserv.getStopsByRouteId(rid, "morning");
			List<ArrivalTimeTable> aAndS = timeserv.getAllStopsWithTimeByRouteId(rid, "morning");

			modelAndView.addObject("arrTime", aAndS);

			modelAndView.addObject("end", "NRIFINTECH");
			modelAndView.addObject("allStops", stops);
		} else {

			List<Stop> stops = timeserv.getStopsByRouteId(rid, "evening");
			List<ArrivalTimeTable> aAndS = timeserv.getAllStopsWithTimeByRouteId(rid, "evening");

			modelAndView.addObject("arrTime", aAndS);
			modelAndView.addObject("start", "NRIFINTECH");
			modelAndView.addObject("allStops", stops);
		}

		return modelAndView;
	}

	// Route route = routeRepo.getById(id);
	// List<Stop> stops = route.ge
	public ModelAndView trackbus() {
		System.out.println("Working");
		ModelAndView modelAndView = new ModelAndView("employee-track-bus");
		return modelAndView;
	}

	@PostMapping("/updateprofile")
	public ResponseEntity<String> update(@RequestParam("eid") long eid,
			@RequestParam("name") String name,
			@RequestParam("contactNo") String contactNo,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User emp1 = (User) session.getAttribute("emp");

		User userv = userService.findByUserName(emp1.getUserName());

		if (!password.equals("")) {
			userService.findByUserName(emp1.getUserName()).setPassword(passwordEncoder.encode(password));

		}

		employeeService.updateEmployeeById(eid, contactNo, name);

		return ResponseEntity.ok("Profile Updated Successfully");
	}

	@GetMapping("/book")
	public ModelAndView book() {
		ModelAndView modelAndView = new ModelAndView("employeeBook");
		modelAndView.addObject("employee", new Employee());
		List<Stop> stops = stopService.getAllStops();
		modelAndView.addObject("stops", stops);
		return modelAndView;
		// Dummy
		// List<Stop> stops = new ArrayList(); // temporary list of stops. to be fetched
		// stops.add(new Stop(1, "Stop1"));
		// stops.add(new Stop(2, "Stop2"));
		// stops.add(new Stop(3, "Stop3"));
		// stops.add(new Stop(4, "Stop4"));
		// stops.add(new Stop(5, "Stop5"));
	}

	@GetMapping("/sos")
	public ModelAndView sos() {
		ModelAndView modelAndView = new ModelAndView("viewsos");
		return modelAndView;
	}

	@GetMapping("/edit")
	public ModelAndView employeeEditForm() {
		ModelAndView modelAndView = new ModelAndView("employeeEdit");
		return modelAndView;
	}

	@PostMapping("/editemployeedetails")
	public Employee editEmployee(@RequestBody Employee employee) {
		return employee;
	}

	// TEst WORK

	@GetMapping("/update1/{id}")
	public User getById(@PathVariable long id, Principal principal, HttpServletResponse response) throws IOException {
		String username = principal.getName();

		// Find the user by ID
		Optional<User> optionalUser = userRepo.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// Check if the user making the request is the same as the user whose
			// information is being requested
			if (user.getUserName().equals(username)) {
				System.out.println(username);
				return user;
			}

		}
		response.sendRedirect("/errorupdate");

		return null;
	}

	@GetMapping("/viewStops")
	public List<ArrivalTimeTable> getAllStopsWithTimeByRouteid(@RequestParam Long rid, @RequestParam String shift) {
		try {
			Route route = routeService.getRouteById(rid);
			List<ArrivalTimeTable> arrivalTimeTableList;
			if (shift.equalsIgnoreCase("morning")) {
				arrivalTimeTableList = arrivalTimeService.findAllByRouteStopId_RouteOrderByMorningArrivalTime(route);
			} else {
				arrivalTimeTableList = arrivalTimeService.findAllByRouteStopId_RouteOrderByEveningArrivalTime(route);
			}
			return arrivalTimeTableList;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@PostMapping(value = "/bookABusByBusId/{busId}/{stopId}")
	public ResponseEntity<String> bookABusByBusId(@RequestBody Long eid, @PathVariable long busId,
			@PathVariable long stopId,
			HttpServletRequest request) {


		System.out.println("Booking Bus For ==============");
		System.out.println("bus id =" + busId + "  empId = " + eid + "stopId = " + stopId + "=============");
		Bus bus = busService.getBusById(busId);
		Stop stop = stopService.getStopById(stopId);
		Employee employee = employeeService.getEmployeeById(eid);
		LocalDate todayDate = LocalDate.now();

		Date date = Date.valueOf(todayDate);

		if (waitingListService.findByE(employee).isPresent()) {
			WaitingList waitingList = waitingListService.findByE(employee).get();
			System.out.println("======================================");
			System.out.println(" EMployee " + employee + " in waitingList");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are already in a waiting list ");

		}

		// prevents an employee to book more than 1 bus/seat for current month
		if (employee.getB() != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only book 1 seat");

		}

		// employee tries to book a filled bus
		if (bus.getAvailableSeats() <= 0) {
			WaitingList waitingList = new WaitingList(0, employee, bus, stop);
			waitingListService.insertWaitingList(waitingList);


			// ---------- Add entry to history table
			Route route = bus.getR();
			String routeDescription = arrivalTimeService.getRouteDescription(route.getRid());
			historyService.add(new History(0L,
					employee.getEid(),
					LocalDate.now(),
					bus.getBid(),
					route.getRid(),
					routeDescription,
					"Waitlist Add"));
			// -------------------------------------------

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Added to waiting List");

		}

		// seats available and employee doesnt have any bus assigned
		bus.setAvailableSeats(bus.getAvailableSeats() - 1);
		busService.insertBus(bus);
		BookingDetails bookingDetails = new BookingDetails(0, employee, bus, date, stop);
		bookingDetailsService.insertBookingDetails(bookingDetails); // add to bookingDetails
		employee.setB(bus);
		System.out.println("======================================");
		System.out.println(employee + " has booked the bus");
		employeeService.insertEmployee(employee);

		// ---------- Add entry to history table
		Route route = bus.getR();
		String routeDescription = arrivalTimeService.getRouteDescription(route.getRid());
		historyService.add(new History(0L,
				employee.getEid(),
				LocalDate.now(),
				bus.getBid(),
				route.getRid(),
				routeDescription,
				"Booking Add"));
		// -------------------------------------------

		return ResponseEntity.ok("Successfully Booked");

	}

	// REMOVE BOOKING OR WAITINGLIST FOR AN EMPLOYEE
	/*
	 * Employee, at his will, may renounce his bus booking or waiting List.
	 * Two cases:
	 * * * Employee has BusId --> remove it
	 * * * Employee is in waitList
	 * This, automatically, triggers the selection of the first employee in
	 * the waiting list
	 */
	@PostMapping("/removebooking")

	public String removeBooking(@RequestBody Long employeeId) {
		String message = "";
		Employee employee = employeeService.getEmployeeById(employeeId);

		if (employee.getB() != null) {
			Bus bus = busService.getBusById(employee.getB().getBid());
			message += String.format("Removed busId=%d from employee=%s\n", employee.getB().getBid(),
					employee.getName());
			employee.setB(null);
			employeeService.insertEmployee(employee);
			bus.setAvailableSeats(bus.getAvailableSeats() + 1);
			busService.insertBus(bus);

			// ---------- Add entry to history table
			Route route = bus.getR();
			String routeDescription = arrivalTimeService.getRouteDescription(route.getRid());
			historyService.add(new History(0L,
					employee.getEid(),
					LocalDate.now(),
					bus.getBid(),
					route.getRid(),
					routeDescription,
					"Booking Remove"));
			// -------------------------------------------

			// ----------------------------------------------------------
			// trigger to add first employee from waiting list to booking

			// List of waitingList associated with the bus
			List<WaitingList> waitingLists = waitingListService.findAllByBOrderByWid(bus);

			// found an employee in waiting list
			if (!waitingLists.isEmpty()) {

				// get topmost waiting List entry
				WaitingList waitingList = waitingLists.get(0);

				// remove entry from waiting list
				waitingListService.deleteWaitingListById(waitingList.getWid());

				// get the employee
				Employee topEmployee = waitingList.getE();

				// stop of the Top employee
				Stop stop = waitingList.getStop();

				// book bus for top employee
				employeeService.bookABusByBusId(topEmployee.getEid(), bus.getBid(), stop.getSid());

				System.out.println(topEmployee.getName() +
						" from waitList has been assigned busID " +
						topEmployee.getB().getBid());

				// ---------- Add entry to history table
				historyService.add(new History(0L,
						topEmployee.getEid(),
						LocalDate.now(),
						bus.getBid(),
						route.getRid(),
						routeDescription,
						"Waitlist Remove"));
				// -------------------------------------------

			}

		}

		// if employee exists in waiting list, remove them
		Optional optionalWaitingList = waitingListService.findByE(employee);
		if (optionalWaitingList.isPresent()) {
			WaitingList waitingList = (WaitingList) optionalWaitingList.get();
			Bus bus = waitingList.getB();
			message += String.format("Removed waitingList entry with WID=%d", waitingList.getWid());
			waitingListService.deleteWaitingListById(waitingList.getWid());

			// ---------- Add entry to history table
			Route route = bus.getR();
			String routeDescription = arrivalTimeService.getRouteDescription(route.getRid());
			historyService.add(new History(0L,
					employee.getEid(),
					LocalDate.now(),
					bus.getBid(),
					route.getRid(),
					routeDescription,
					"Waitlist Remove"));
			// -------------------------------------------
		}

		return message;

	}

	@GetMapping("get-employee-dashboard-details/{eid}")
	public Map<String, String> getEmployeeDashboardDetails(@PathVariable long eid) {
		Map<String, String> employeeDashboardDetails = new HashMap<>();
		Employee employee = employeeService.getEmployeeById(eid);
		employeeDashboardDetails.put("name", employee.getName());
		employeeDashboardDetails.put("phoneNo", employee.getContactNo());
		employeeDashboardDetails.put("email", employee.getUser().getUserName());
		if (employee.getB() != null) {
			employeeDashboardDetails.put("busId", "" + employee.getB().getBid());
			employeeDashboardDetails.put("bookingStatus", "Bus Seat Reserved");
		} else {
			employeeDashboardDetails.put("busId", "NA");
			Optional<WaitingList> waitList = waitingListService.findByE(employee);
			if (waitList.isPresent()) {
				employeeDashboardDetails.put("bookingStatus", "In Waitlist");
			} else {
				employeeDashboardDetails.put("bookingStatus", "Not booked");
			}
		}
		return employeeDashboardDetails;
	}

	@GetMapping("/get-booking-details/{eid}")
	public Map<String, String> getBookingDetails(@PathVariable long eid) {
		Map<String, String> employeeBookingDetails = new HashMap<>();
		Employee employee = employeeService.getEmployeeById(eid);
		String bookingId = "NA";
		String busNo = "NA";
		String driverName = "NA";
		String driverContactNo = "NA";
		String busStatus = "NA";
		String homeStopName = "NA";
		System.out.println("hello");
		if (employee.getB() != null) {
			Bus bus = employee.getB();
			busNo = "" + bus.getBid();
			Driver driver = bus.getD();
			busStatus = bus.getStartTime() != null ? bus.getStartTime().toString() : "Journey Not started";
			if (driver != null) {
				driverName = driver.getDriverName();
				driverContactNo = driver.getDriverContactNo();
			}

			BookingDetails bookingDetails = bookingDetailsService.findMostRecentBooking(employee, bus);
			if (bookingDetails != null) {
				bookingId = "" + bookingDetails.getBookingId();
				homeStopName = bookingDetails.getStop().getName();
				
			}
		}

		employeeBookingDetails.put("bookingId", bookingId);
		employeeBookingDetails.put("busNo", busNo);
		employeeBookingDetails.put("driverName", driverName);
		employeeBookingDetails.put("driverContactNo", driverContactNo);
		employeeBookingDetails.put("busStatus", busStatus);
		employeeBookingDetails.put("homeStopName", homeStopName);
		return employeeBookingDetails;
	}

}