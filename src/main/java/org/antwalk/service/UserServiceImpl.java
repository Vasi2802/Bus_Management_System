package org.antwalk.service;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

import org.antwalk.dao.UserDao;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.User;
import org.antwalk.repository.DriverRepo;
import org.antwalk.repository.EmployeeRepo;
import org.antwalk.repository.UserRepo;
import org.antwalk.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserDao userrepo;
	
	
	@Autowired
	private EmailService emailserv;
	
	
	@Autowired
	private EmployeeRepo emprepo;

	@Autowired
	private DriverRepo driverrepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Override
	@Transactional
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userrepo.findByUserName(userName);
	}
	
	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user = new User();
		 // assign user details to the user object
		user.setUserName(crmUser.getEmail());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		
		Employee emp = new Employee();
		emp.setName(crmUser.getFullName());
		emp.setContactNo(crmUser.getContactNo());
		emp.setUser(user);
		
//		user.setFirstName(crmUser.getFirstName());
//		user.setLastName(crmUser.getLastName());
//		user.setEmail(crmUser.getEmail());

		// give user default role of "employee"
		user.setRole("ROLE_EMPLOYEE");
		
//		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		 // save user in the database
		userrepo.save(user);
		emprepo.save(emp);
		String msg = "Dear "+emp.getName()+",\r\n"
				+ "\r\n"
				+ "We are delighted to welcome you to NRIFT Bus Service and thank you for choosing us for your travel needs. We are committed to providing you with a safe, comfortable, and enjoyable travel experience.\r\n"
				+ "\r\n"
				+ "As a new customer, we would like to extend a warm welcome and provide you with some helpful information about our service:\r\n"
				+ "\r\n"
				+ "Our buses are equipped with air conditioning, comfortable seats, and ample legroom to ensure a pleasant journey.\r\n"
				+ "\r\n"
				+ "We offer a variety of routes and schedules to suit your travel needs. You can find our schedule and route map on our website or by contacting our customer service team.\r\n"
				+ "\r\n"
				+ "Our experienced and professional drivers are dedicated to providing you with a safe and smooth ride. We pride ourselves on our excellent safety record.\r\n"
				+ "\r\n"
				+ "We offer affordable fares and special discounts for frequent travelers. You can also earn rewards points through our loyalty program.\r\n"
				+ "\r\n"
				+ "If you have any questions or concerns, please do not hesitate to contact our customer service team at 100012223 or busdemoproject@gmail.com. We are here to assist you in any way we can.\r\n"
				+ "\r\n"
				+ "Once again, welcome to NRIFT Bus Service. We look forward to serving you and providing you with an outstanding travel experience.\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "\r\n"
				+ "Bus Admin\r\n"
				+ "NRIFT Bus Service";
		
		emailserv.sendEmail(user.getUserName(), "Welcome to NRIFT Bus Service!", msg);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userrepo.findByUserName(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		Collection<? extends GrantedAuthority> authorities = mapRoleToAuthority(user.getRole());

	    return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
	}

	public Collection<? extends GrantedAuthority> mapRoleToAuthority(String role) {
	    return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

	@Override
	public boolean canUpdateEmployee(Principal principal, Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public void savedriver(CrmUser crmUser) {
		User user = new User();
		 // assign user details to the user object
		user.setUserName(crmUser.getEmail());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		
		Driver driver = new Driver();
		driver.setDriverName(crmUser.getFullName());
		driver.setDriverContactNo(crmUser.getContactNo());
		driver.setUser(user);
		
//		user.setFirstName(crmUser.getFirstName());
//		user.setLastName(crmUser.getLastName());
//		user.setEmail(crmUser.getEmail());

		// give user default role of "employee"
		user.setRole("ROLE_DRIVER");
		
//		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		 // save user in the database
		userrepo.save(user);
		driverrepo.save(driver);
		String msg = "Dear "+driver.getDriverName()+",\r\n"
				+ "\r\n"
				+ "We are delighted to welcome you to NRIFT Bus Service and thank you for choosing us for your travel needs. We are committed to providing you with a safe, comfortable, and enjoyable travel experience.\r\n"
				+ "\r\n"
				+ "As a new customer, we would like to extend a warm welcome and provide you with some helpful information about our service:\r\n"
				+ "\r\n"
				+ "Our buses are equipped with air conditioning, comfortable seats, and ample legroom to ensure a pleasant journey.\r\n"
				+ "\r\n"
				+ "We offer a variety of routes and schedules to suit your travel needs. You can find our schedule and route map on our website or by contacting our customer service team.\r\n"
				+ "\r\n"
				+ "Our experienced and professional drivers are dedicated to providing you with a safe and smooth ride. We pride ourselves on our excellent safety record.\r\n"
				+ "\r\n"
				+ "We offer affordable fares and special discounts for frequent travelers. You can also earn rewards points through our loyalty program.\r\n"
				+ "\r\n"
				+ "If you have any questions or concerns, please do not hesitate to contact our customer service team at 100012223 or busdemoproject@gmail.com. We are here to assist you in any way we can.\r\n"
				+ "\r\n"
				+ "Once again, welcome to NRIFT Bus Service. We look forward to serving you and providing you with an outstanding travel experience.\r\n"
				+ "\r\n"
				+ "Best regards,\r\n"
				+ "\r\n"
				+ "Bus Admin\r\n"
				+ "NRIFT Bus Service";
		
		emailserv.sendEmail(user.getUserName(), "Welcome to NRIFT Bus Service!", msg);
	}

	/*
	 * @Override
	 * 
	 * @Transactional public User findByEmail(String email) { // TODO Auto-generated
	 * method stub System.out.println("third"+email); return
	 * userDao.findByEmail(email); }
	 * 
	 * public boolean canUpdateEmployee(Principal principal, Long id) { User
	 * employee = employeeRepository.getById(id); if (employee == null) { return
	 * false; } String username = principal.getName(); return
	 * employee.getUserName().equals(username); }
	 */
}
