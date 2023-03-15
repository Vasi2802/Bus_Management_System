package org.antwalk.service;

import java.util.List;

import org.antwalk.entity.Employee;
import org.antwalk.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	
	@Transactional
	public void updateEmployeeById(Long id,String contact, String name) {
		empRepo.getById(id).setContactNo(contact);
		empRepo.getById(id).setName(name);
		
	}
	
}
