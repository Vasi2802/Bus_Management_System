package org.antwalk.service;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.BookingDetailsRepo;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.WaitingListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo empRepo;

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private WaitingListRepo waitingListRepo;

	@Autowired
	private BookingDetailsRepo bookingDetailsRepo;

	public Employee insertEmployee(Employee e) {
		return empRepo.save(e);
	}

	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}

	public Employee getEmployeeById(long id) {
		return empRepo.findById(id).get();
	}

	public String deleteEmployeeById(long id) {
		empRepo.deleteById(id);
		return "Employee Deleted";
	}

	public String updateEmployeeById(Employee e, long id, Principal p) {
		// List<Employee> empList = empRepo.findAll();
		// String username =p.getName();
		//
		// // Find the user by ID
		// Optional<Employee> optionalUser = empRepo.findById(id);
		//
		// if (optionalUser.isPresent()) {
		// Employee user = optionalUser.get();
		// // Check if the user making the request is the same as the user whose
		// information is being requested
		// if (user.getEmail().equals(username)) {
		// empRepo.save(e);
		// return "Success";
		// }
		// }
		// return "Failure";

		/*
		 * for(Employee obj:empList) {
		 * if(obj.getEid() == id) {
		 * if(e.getEid() == id) {
		 * empRepo.save(e);
		 * return "Employee Updated";
		 * }
		 * 
		 * else {
		 * return
		 * "Employee exists but your input id does not match with the existing Employee id"
		 * ;
		 * }
		 * 
		 * }
		 * }
		 */
		return "Employee does not exist";

	}

	public String removeBooking(long employeeId) {
		
		String message = "";
		Employee employee = empRepo.findById(employeeId).get(); 

		// if employee has a bus ID, remove it
		
		if (employee.getB() != null) {
			Bus bus = busRepo.getById(employee.getB().getBid());
			message += String.format("Removed busId=%d from employee=%s\n", employee.getB().getBid(), employee.getName());
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

				String messageForBooking = bookABusByBusId(topEmployee.getEid(), bus.getBid());

				System.out.println(messageForBooking);

				System.out
						.println(topEmployee.getName() + " from waitList has been assigned busID "
								+ topEmployee.getB().getBid());

			}

		}
		return message;
	}

	public String bookABusByBusId(long eid, long busId) {

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

		return String.format("Hi %s!\nYou have successfully booked Bus with id=%d", employee.getName(), bus.getBid());

	}
}
