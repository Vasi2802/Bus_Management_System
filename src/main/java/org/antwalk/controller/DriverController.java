package org.antwalk.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Attendance;
import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.repository.DriverRepo;
import org.antwalk.service.ArrivalTimeService;
import org.antwalk.service.AttendanceService;
import org.antwalk.service.BookingDetailsService;
import org.antwalk.service.BusService;
import org.antwalk.service.DriverService;
import org.antwalk.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/driver")
public class DriverController {
	
	@Autowired
	DriverRepo driverRepo;
	
	@Autowired
	DriverService driverService;
	
	
	@Autowired
	ArrivalTimeService arrivalTimeService;

	@Autowired
	private BookingDetailsService bookingDetailsService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AttendanceService attendanceService;
	
	
	@Autowired
	BusService buserv;
	
	@PostMapping("/insert")
	public Driver insert(@RequestBody Driver d) {
		return driverService.insertDriver(d);
	}
	
	@GetMapping("/getall")
	public List<Driver> getAll(){
		return driverService.getAllDrivers();
	}
	
	@GetMapping("/getbyid/{id}")
	public Driver getById(@PathVariable long id) {
		return driverService.getDriverById(id);
	}
	
	@DeleteMapping("/deletebyid/{id}")
	public String deleteById(@PathVariable long id) {
		driverService.deleteDriverById(id);
		return "Deleted";
	}

	@PutMapping("/update/{id}")
	public String update(@RequestBody Driver d, @PathVariable long id) {

		List<Driver> driverList = driverService.getAllDrivers();
		for(Driver obj:driverList) {
			if(obj.getDid() == id) {
				if(d.getDid() == id) {
					driverService.insertDriver(d);
					return "Updated";
				}
				
				else {
					return "Id doesn't match";
				}
				
			}
		}
		return "Id does not exist";
		
	}
	
	@GetMapping("/routedetails")
	public ModelAndView booking(HttpServletRequest request) {
		HttpSession session = request.getSession();
	    User user = (User)session.getAttribute("driver");
		Driver driver = driverService.getDriverById(user.getDriver().getDid());
		
		ModelAndView modelAndView = new ModelAndView("route-details");
		 if(driver.getBus()==null) {
		    	modelAndView = new ModelAndView("error-track-bus");
		    	return modelAndView;
		    }
		    
		
		Long rid = driver.getBus().getR().getRid();
	    
	    LocalTime currentTime = LocalTime.now(); // get the current time
	    LocalTime noon = LocalTime.of(12, 0); // set noon time to 12:00 PM
	    
		
		  if (currentTime.isBefore(noon)) { List<Stop> stops=
		  arrivalTimeService.getStopsByRouteId(rid, "morning"); List<ArrivalTimeTable> aAndS =
		  arrivalTimeService.getAllStopsWithTimeByRouteId(rid, "morning");
		  
		  modelAndView.addObject("arrTime", aAndS);
		  
//		  modelAndView.addObject("end", "NRIFINTECH");
		  modelAndView.addObject("allStops", stops); } else {
		 
	    	List<Stop> stops= arrivalTimeService.getStopsByRouteId(rid, "evening");
	    	List<ArrivalTimeTable> aAndS = arrivalTimeService.getAllStopsWithTimeByRouteId(rid, "evening");
	    
	    	modelAndView.addObject("arrTime", aAndS);
	    	modelAndView.addObject("start", "NRIFINTECH");
	    	modelAndView.addObject("allStops", stops);
	    }
		  
		return modelAndView;
	}
	
	@PostMapping("/changestatus/{bid}")
	public String insert1(@PathVariable long bid) {
		String msg;
		System.out.println(bid);
		Bus bus = buserv.getBusById(bid);
		if(bus.getActive().equals("NO")){
			bus.setActive("YES");
		}
		else {
			bus.setActive("NO");
		}
		
		buserv.updateBusById(bus, bid);
		
		
		return null;
	}

	@GetMapping("get-all-passengers") 
	public ResponseEntity<Object> getAllPassengers(HttpServletRequest httpServletRequest){
		User user = (User)httpServletRequest.getSession().getAttribute("driver");
		Driver driver = user.getDriver();
		Object passengers = driverService.getAllPassengers(driver);
		return ResponseEntity.status(HttpStatus.OK).body((Object)passengers);
	}

	@GetMapping("/onboard-passenger/{employeeId}/{stopId}")
	public String onboardPassenger(@PathVariable long employeeId, @PathVariable long stopId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		BookingDetails bookingDetails = bookingDetailsService.getBookingDetailsByE(employee);
		bookingDetails.setIsBoarded(true);
		String stopName = bookingDetails.getStop().getName();
		String employeeName = bookingDetails.getE().getName();
		String shift = "Morning";
		LocalTime now = LocalTime.now();
		if(now.getHour()>12){
			shift = "Evening";
		}

		
		// update boarding status in booking details
		bookingDetailsService.insertBookingDetails(bookingDetails);

		// save in attendance table
		LocalDate today = LocalDate.now();
		Attendance attendance = new Attendance(null,bookingDetails.getB().getBid(), employee.getEid(), employeeName, stopName, shift, today);
		attendanceService.save(attendance);
		return "Successful";
	}

	@GetMapping("/clear-onboarding")
	public void clearOnboarding(){
		driverService.clearBoardingStatus();
	}

}
