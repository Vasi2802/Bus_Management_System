package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Admin;
import org.antwalk.entity.Route;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.repository.AdminRepo;
import org.antwalk.repository.RouteRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AdminServiceTest {

	@Autowired
	private AdminService adminService;
	
	@MockBean
	private AdminRepo adminRepo;
	
	private String contactNo;
	private User user;
	
	@BeforeEach
	void setUp() {
		contactNo = "1234567890";
		user = new User((long) 10, "admin@nrifintech.com", "fun123", "ROLE_ADMIN");
	}
	
	@Test
	void testInsertAdmin() {
		Admin expected = new Admin(1, contactNo, user);
		when(adminRepo.save(expected)).thenReturn(expected);
		
		Admin actual = adminService.insertAdmin(expected);
		assertEquals(expected, actual);
	}

	@Test
	void testGetAllAdmin() {
		List<Admin> expected = new ArrayList<>();
		Admin a1 = new Admin(2, contactNo, user);
		Admin a2 = new Admin(3, "45745746400", user);
		expected.add(a1);
		expected.add(a2);

		when(adminRepo.findAll()).thenReturn(expected);
		
		List<Admin> actual = adminService.getAllAdmin();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

	@Test
	void testGetAdminById() {
		long id = 5;
		Admin expected = new Admin(id, contactNo, user);
		when(adminRepo.findById(id)).thenReturn(Optional.of(expected));
		
		Admin actual = adminService.getAdminById(id);
		assertEquals(expected,actual);
	}

	@Test
	void testDeleteAdminById() {
		Admin a = new Admin(8, contactNo, user);
		adminService.deleteAdminById(8);
		
		verify(adminRepo, times(1)).deleteById((long) 8);
	}

	@Test
	void testUpdateAdminById() {
		String expected1 = "Admin Updated";
		String expected2 = "Admin exists but your input id does not match with the existing Admin id";
		String expected3 = "Admin does not exist";
		
		Admin a = new Admin(10, contactNo, user);
		List<Admin> adminList = new ArrayList<>();
		adminList.add(a);
		
		a.setContactNo("9876543210");
		
		when(adminRepo.findAll()).thenReturn(adminList);
		when(adminRepo.save(a)).thenReturn(a);
		
		String actual1 = adminService.updateAdminById(a, 10);
		assertEquals(expected1, actual1);
		
		when(adminRepo.save(new Admin(12, contactNo, user))).thenReturn(new Admin(12, contactNo, user));
		String actual2 = adminService.updateAdminById(new Admin(12, contactNo, user), 10);
		assertEquals(expected2, actual2);
		
		when(adminRepo.save(new Admin(20, contactNo, user))).thenReturn(new Admin(20, contactNo, user));
		String actual3 = adminService.updateAdminById(new Admin(20, contactNo, user), 20);
		assertEquals(expected3, actual3);
	}

}
