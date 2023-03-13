package org.antwalk.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Employee;
import org.antwalk.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo empRepo;
	
	public Employee insertEmployee(Employee e) {
		return empRepo.save(e);
	}
	
	public List<Employee> getAllEmployees(){
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
//		List<Employee> empList = empRepo.findAll();
//		 String username =p.getName();
//          
//          // Find the user by ID
//          Optional<Employee> optionalUser = empRepo.findById(id);
//          
//          if (optionalUser.isPresent()) {
//              Employee user = optionalUser.get();
//              // Check if the user making the request is the same as the user whose information is being requested
//              if (user.getEmail().equals(username)) {
//            	  empRepo.save(e);
//            	  return "Success";
//              }
//          }
//          return "Failure";
          
	/*	for(Employee obj:empList) {
			if(obj.getEid() == id) {
				if(e.getEid() == id) {
					empRepo.save(e);
					return "Employee Updated";
				}
				
				else {
					return "Employee exists but your input id does not match with the existing Employee id";
				}
				
			}
		}
	*/
		return "Employee does not exist";
	
	}
}
