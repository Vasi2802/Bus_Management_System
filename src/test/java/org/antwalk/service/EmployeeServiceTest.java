package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.RouteRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class EmployeeServiceTest {

	@Autowired
	private EmployeeService empService;
	
	@MockBean
	private EmployeeRepo empRepo;
	
	private String name;
	private String contactNo;
	private Bus b;
	private User user;
	
	@BeforeEach
	void setUp() {
		name = "Subhashree Mitra";
		contactNo = "1234567890";
		b = new Bus(1, 20, 10, LocalTime.of(7, 0), new Driver(3, "Kanai", "2345678901", new User((long) 1, "kanai@driver.nrifintech.com", "fun123", "ROLE_DRIVER")), new Route(2, new Stop(2,"s2"), new Stop(8, "s8")));
		user = new User((long) 5, "subhashreem@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE");
	}
	
	@Test
	void testInsertEmployee() {
		Employee expected = new Employee(1, name, contactNo, b, user);
		when(empRepo.save(expected)).thenReturn(expected);
		
		Employee actual = empService.insertEmployee(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllEmployees() {
		List<Employee> expected = new ArrayList<>();
		Employee e1 = new Employee(2, name, contactNo, b, user);
		Employee e2 = new Employee(3, "Anupam Adhikari", contactNo, b, new User((long) 7, "anupama@trainee.nrifintech.com", "fun123", "ROLE_EMPLOYEE"));
		expected.add(e1);
		expected.add(e2);

		when(empRepo.findAll()).thenReturn(expected);
		
		List<Employee> actual = empService.getAllEmployees();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetEmployeeById() {
		long id = 5;
		Employee expected = new Employee(id, name, contactNo, b, user);
		when(empRepo.findById(id)).thenReturn(Optional.of(expected));
		
		Employee actual = empService.getEmployeeById(id);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteEmployeeById() {
		Employee e = new Employee(8, name, contactNo, b, user);
		empService.deleteEmployeeById(8);
		
		verify(empRepo, times(1)).deleteById((long) 8);
	}

	
	@Test
	void testUpdateEmployeeById() {
		Employee expected = new Employee(10, name, contactNo, b, user);
		when(empRepo.getById((long) 10)).thenReturn(expected);
		empService.updateEmployeeById((long) 10, "0987654321", "Shreyansh Sahu");
		
//		System.out.println(expected.getContactNo() + "      " + expected.getName());
		assertEquals(expected.getContactNo(), "0987654321");
		assertEquals(expected.getName(), "Shreyansh Sahu");		

		
	}

}
