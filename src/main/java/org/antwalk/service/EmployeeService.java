package org.antwalk.service;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.BookingDetails;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.antwalk.entity.History;
import org.antwalk.entity.Route;
import org.antwalk.entity.WaitingList;
import org.antwalk.repository.BookingDetailsRepo;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.WaitingListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.antwalk.entity.Employee;
import org.antwalk.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private WaitingListRepo waitingListRepo;

	@Autowired
	private BookingDetailsRepo bookingDetailsRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EmployeeRepo empRepo;

	@Autowired
	private ArrivalTimeService arrivalTimeService;

	@Autowired
	private HistoryService historyService;

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

	@Transactional
	public void updateEmployeeById(Long id, String contact, String name) {
		empRepo.getById(id).setContactNo(contact);
		empRepo.getById(id).setName(name);
	}

	public String removeBooking(long employeeId) {

		String message = "";
		Employee employee = empRepo.findById(employeeId).get();

		// if employee has a bus ID, remove it

		if (employee.getB() != null) {
			Bus bus = busRepo.getById(employee.getB().getBid());
			message += String.format("Removed busId=%d from employee=%s\n", employee.getB().getBid(),
					employee.getName());
			employee.setB(null);
			empRepo.save(employee);
			bus.setAvailableSeats(bus.getAvailableSeats() + 1);
			busRepo.save(bus);

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

		Optional optionalWaitingList = waitingListRepo.findByE(employee);
		if (optionalWaitingList.isPresent()) {
			WaitingList waitingList = (WaitingList) optionalWaitingList.get();
			message += String.format("Removed waitingList entry with WID=%d", waitingList.getWid());
			waitingListRepo.deleteById(waitingList.getWid());

			// ---------- Add entry to history table
			Bus bus = waitingList.getB();
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

	public String bookABusByBusId(long eid, long busId) {

		System.out.println("Booking Bus For ==============");
		System.out.println("bus id =" + busId + "  empId = " + eid + "=============");
		Bus bus = busRepo.findById(busId).get();
		Employee employee = empRepo.findById(eid).get();
		LocalDate todayDate = LocalDate.now();
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

		return String.format("Hi %s!\nYou have successfully booked Bus with id=%d", employee.getName(), bus.getBid());

	}

	@Transactional
	public void deleteemployee(Long id) {
		Employee emp = entityManager.find(Employee.class, id);
		if (emp != null) {
			entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
			entityManager.remove(emp);
			entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
		}
	}
}
