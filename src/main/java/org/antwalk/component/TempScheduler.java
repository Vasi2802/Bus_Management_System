package org.antwalk.component;

import java.util.List;

import org.antwalk.entity.Employee;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.WaitingListRepo;
import org.antwalk.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TempScheduler {

	@Autowired
	WaitingListRepo waitingListRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	EmployeeService employeeService;

    @Scheduled(cron = "0 0 0 1 * *")
	public void clearWaitingListTable() {
		waitingListRepo.deleteAll();
		List<Employee> employees = employeeRepo.findAll();
		for(Employee employee: employees){
			employeeService.removeBooking(employee.getEid());
		}
		System.out.println("WaitingList Table Cleared. All buses deallocated");
		
	}

}
