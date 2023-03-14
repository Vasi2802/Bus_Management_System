package org.antwalk.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.BookingDetailsRepo;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.RouteRepo;
import org.antwalk.repository.StopRepo;
import org.antwalk.repository.UserRepo;
import org.antwalk.repository.WaitingListRepo;
import org.antwalk.service.ArrivalTimeService;
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

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeRepo empRepo;

	@Autowired
	RouteRepo routeRepo;
	
	@Autowired
	ArrivalTimeService timeserv;
	
	@Autowired
	BusRepo busRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	StopRepo stopRepo;

	@Autowired
	BookingDetailsRepo bookingDetailsRepo;

	@Autowired
	WaitingListRepo waitingListRepo;

	@PostMapping("/insert")
	public Employee insert(@RequestBody Employee e) {
		return empRepo.save(e);
	}

	@GetMapping("/getall")
	public List<Employee> getAll() {
		return empRepo.findAll();
	}

	@GetMapping("/getbyid/{id}")
	public Employee getById1(@PathVariable long id) {
		return empRepo.findById(id).get();
	}

	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		empRepo.deleteById(id);
		return "Deleted";
	}

	@PutMapping("/update/{id}")
	public String update(@RequestBody Employee e, @PathVariable long id) {
		List<Employee> empList = empRepo.findAll();
		for (Employee obj : empList) {
			if (obj.getEid() == id) {
				if (e.getEid() == id) {
					empRepo.save(e);
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
	    User emp = (User)session.getAttribute("emp");
	    if(emp.getEmployee().getB()==null) {
	    	modelAndView = new ModelAndView("error-seat-form");
	    	return modelAndView;
	    }	
	    

		return modelAndView;
	}

	@GetMapping("/showbookingdetails")
	public ModelAndView booking(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("employee-booking-details");
		HttpSession session = request.getSession();
	    User emp = (User)session.getAttribute("emp");
	    if(emp.getEmployee().getB()==null) {
	    	modelAndView = new ModelAndView("error-booking-details");
	    	return modelAndView;
	    }	

	    
		return modelAndView;
	}
	
	@GetMapping("/trackbus")
	public ModelAndView trackbus(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("employee-track-bus");
		HttpSession session = request.getSession();
	    User emp = (User)session.getAttribute("emp");
	    if(emp.getEmployee().getB()==null) {
	    	modelAndView = new ModelAndView("error-track-bus");
	    	return modelAndView;
	    }
	    
	    Long rid = emp.getEmployee().getB().getR().getRid();
	    
	    LocalTime currentTime = LocalTime.now(); // get the current time
	    LocalTime noon = LocalTime.of(12, 0); // set noon time to 12:00 PM
	    
		
		  if (currentTime.isBefore(noon)) { List<Stop> stops=
		  timeserv.getStopsByRouteId(rid, "morning"); List<ArrivalTimeTable> aAndS =
		  timeserv.getAllStopsWithTimeByRouteId(rid, "morning");
		  
		  modelAndView.addObject("arrTime", aAndS);
		  
		  modelAndView.addObject("end", "NRIFINTECH");
		  modelAndView.addObject("allStops", stops); } else {
		 
	    	List<Stop> stops= timeserv.getStopsByRouteId(rid, "evening");
	    	List<ArrivalTimeTable> aAndS = timeserv.getAllStopsWithTimeByRouteId(rid, "evening");
	    
	    	modelAndView.addObject("arrTime", aAndS);
	    	modelAndView.addObject("start", "NRIFINTECH");
	    	modelAndView.addObject("allStops", stops);
	    }
	    
		return modelAndView;
	}
//	Route route = routeRepo.getById(id);
//	List<Stop> stops = route.ge

	@GetMapping("/book")
	public ModelAndView book() {
		ModelAndView modelAndView = new ModelAndView("employeeBook");
		modelAndView.addObject("employee", new Employee());
		List<Stop> stops = stopRepo.findAll();
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

	@PostMapping(value = "/bookABusByBusId/{busId}")
	public String bookABusByBusId(@RequestBody Long eid, @PathVariable long busId,HttpServletRequest request) {

		System.out.println("Booking Bus For ==============");
		System.out.println("bus id =" + busId + "  empId = " + eid + "=============");
		Bus bus = busRepo.findById(busId).get();
		Employee employee = empRepo.findById(eid).get();
		LocalDate todayDate = LocalDate.now(); // current date goes in booking details
		// .withDayOfMonth(1); // gets day 1 of current month
		// LocalDate nextMonthDay1 = todaydate.plusMonths(1); // gets first day of next
		// month
		// System.out.println(todaydate.plusMonths(1).toString());
		Date date = Date.valueOf(todayDate);

		// prevents an employee in waitingList to book a bus
		if (waitingListRepo.findByE(employee).isPresent()) {
			WaitingList waitingList = waitingListRepo.findByE(employee).get();
			System.out.println("======================================");
			System.out.println(" EMployee " + employee + " in waitingList");
			return String.format(
					"Sorry %s. \nYou are already in the waiting List. \nYour waitList details are WID=%d\t EID=%d\t BusId=%d. \nTo book, you must cancel your waiting List",
					employee.getName(), waitingList.getWid(), waitingList.getE().getEid(), waitingList.getB().getBid());
		}

		// prevents an employee to book more than 1 bus/seat for current month
		if (employee.getB() != null) {
			System.out.println("======================================");
			System.out.println(" EMployee " + employee + " has a booking");
			return String.format("Sorry %s. \nYou can book only 1 bus seat in a month. \nYour current bus ID is %s",
					employee.getName(), employee.getB().getBid());
		}

		// employee tries to book a filled bus
		if (bus.getAvailableSeats() <= 0) {
			WaitingList waitingList = new WaitingList(0, employee, bus);
			waitingListRepo.save(waitingList);
			return String.format("Hi %s!\nYou have been added to waitlist for bus with id=%d.\n Your waitlist id=%d",
					employee.getName(), bus.getBid(), waitingList.getWid());
		}

		// seats available and employee doesnt have any bus assigned
		bus.setAvailableSeats(bus.getAvailableSeats() - 1);
		busRepo.save(bus);
		BookingDetails bookingDetails = new BookingDetails(0, employee, bus, date);
		bookingDetailsRepo.save(bookingDetails); // add to bookingDetails
		employee.setB(bus);
		System.out.println("======================================");
		System.out.println(employee + " has booked the bus");
		empRepo.save(employee);
		
		
		HttpSession session = request.getSession();
		
	    User emp = (User)session.getAttribute("emp");
	    emp.getEmployee().setB(bus);
	    session.setAttribute("emp", emp);
	    
		return String.format("Hi %s!\nYou have successfully booked Bus with id=%d", employee.getName(), bus.getBid());

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
	public String removeBooking(@RequestBody Long employeeId,HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
	    User emp = (User)session.getAttribute("emp");
	    emp.getEmployee().setB(null);
	    
		String message = "";
		Employee employee = empRepo.findById(employeeId).get(); // to fetched from session data ( __INCOMPLETE__ )

		// if employee has a bus ID, remove it
		
		
		if (employee.getB() != null) {
			Bus bus = busRepo.getById(employee.getB().getBid());
			message += String.format("Removed busId=%d from employee=%s\n", employee.getB().getBid(),
					employee.getName());
			employee.setB(null);
			empRepo.save(employee);
			bus.setAvailableSeats(bus.getAvailableSeats() + 1);
			busRepo.save(bus);

			// ----------------------------------------------------------
			// trigger to add first employee from waiting list to booking

			// List of waitingList associated with the bus
			List<WaitingList> waitingLists = waitingListRepo.findAllByBOrderByWid(bus);

			// found an employee in waiting list
			if (!waitingLists.isEmpty()) {

				// get topmost waiting List entry
				WaitingList waitingList = waitingLists.get(0);

				// remove entry from waiting list
				waitingListRepo.deleteById(waitingList.getWid());

				// get the employee
				Employee topEmployee = waitingList.getE();

				String messageForBooking = bookABusByBusId(topEmployee.getEid(), bus.getBid(), null);

				System.out.println(messageForBooking);

				// // add booking details
				// LocalDate today = LocalDate.now();
				// BookingDetails bookingDetails = new BookingDetails(0, employee, bus,
				// Date.valueOf(today));

				// // assign bus Id to employee
				// employee.setB(bus);
				// empRepo.save(employee);

				// // reduce bus Seats
				// bus.setAvailableSeats(bus.getAvailableSeats() - 1);
				// busRepo.save(bus);

				System.out
						.println(topEmployee.getName() + " from waitList has been assigned busID "
								+ topEmployee.getB().getBid());

			}

		}

		// if employee exists in waiting list, remove them
		Optional optionalWaitingList = waitingListRepo.findByE(employee);
		if (optionalWaitingList.isPresent()) {
			WaitingList waitingList = (WaitingList) optionalWaitingList.get();
			message += String.format("Removed waitingList entry with WID=%d", waitingList.getWid());
			waitingListRepo.deleteById(waitingList.getWid());
		}

		
		return message;

	}
}