package org.antwalk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.antwalk.entity.Driver;
import org.antwalk.entity.Employee;
import org.antwalk.entity.User;
import org.antwalk.repository.UserRepo;
import org.antwalk.service.DriverService;
import org.antwalk.service.EmployeeService;
import org.antwalk.service.UserService;
import org.antwalk.user.CrmUser;
import org.antwalk.user.UpdateProfile;
import javax.validation.Valid;

import org.antwalk.entity.User;
import org.antwalk.service.UserService;
import org.antwalk.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	DriverService driverService;
	
	@Autowired
	EmployeeService empRepo;

	@Autowired
	UserRepo userRepo;

	@GetMapping("/")
	public String showLogin() {

		return "login";
	}

	@GetMapping("/driver")
	public String showHome(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
	    User user = (User)session.getAttribute("driver");
		Driver driver = driverService.getDriverById(user.getDriver().getDid());
		
		String res = driver.getBus().getR().getActive();
		
		model.addAttribute("res",res);
		return "driver";
	}

	@GetMapping("/employee")
	public String showLeaders() {

		return "employee";
	}

	// add request mapping for /systems

	@GetMapping("/admin")
	public String showSystems() {

		return "admin";
	}

	@GetMapping("/admin/adddriver")
	public String adddriver(Model model) {

		CrmUser user = new CrmUser();
		user.setPassword("NRIFINTECH");
		user.setMatchingPassword("NRIFINTECH");
		model.addAttribute("crmUser", user);

		model.addAttribute("defaultpass", "NRIFINTECH");
		return "registration-form-driver";
	}

	@PostMapping("/admin/register/processRegistrationFormdriver")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser,
			BindingResult theBindingResult, Model theModel) {

		String userName = theCrmUser.getEmail();
//	logger.info("Processing registration form for: " + userName);

		// form validation
		if (theBindingResult.hasErrors()) {
			return "registration-form-driver";
		}

		// check the database if user already exists
		User existing = userService.findByUserName(userName);

		if (existing != null) {
			theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");

//		logger.warning("User name already exists.");
			return "registration-form-driver";
		}
		/*
		 * String userEmail = theCrmUser.getEmail(); System.out.println(userEmail); User
		 * existing1 = userService.findByEmail(userEmail);
		 * 
		 * if (existing1 != null){ theModel.addAttribute("crmUser", new CrmUser());
		 * theModel.addAttribute("registrationError", "Email already exists.");
		 * 
		 * logger.warning("Email already exists."); return "registration-form"; }
		 */
		// create user account
		userService.savedriver(theCrmUser);

//    logger.info("Successfully created user: " + userName);

		theModel.addAttribute("confirmregister", "Successfully Registered");
		return "admin";
	}

	@GetMapping("/driver/preview")
	public String showDriverpreview() {

		return "preview";
	}

	@GetMapping("/employee/editprofile")

	public String edit(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		User emp = (User) session.getAttribute("emp");
		UpdateProfile updateProfile = new UpdateProfile();

		Employee empinfo = empRepo.getEmployeeById(emp.getEmployee().getEid());
		User userinfo = userRepo.getById(emp.getId());

		updateProfile.setFullName(empinfo.getName());
		updateProfile.setContactNo(empinfo.getContactNo());

		model.addAttribute("updateProfile", updateProfile);
		return "edit-employee-profile";
	}

	@PostMapping("/employee/editprofileprocess")
	public String editconfirm(HttpServletRequest request,@ModelAttribute("updateProfile") UpdateProfile user,Model model) {
			
			
			  System.out.println(user.getContactNo());
			  System.out.println(user.getFullName());
			  System.out.println(user.getPassword()); HttpSession session =
			  request.getSession(); User emp = (User)session.getAttribute("emp");
			  
			  User userv = userService.findByUserName(emp.getUserName()); Employee empp =
			  empRepo.getEmployeeById(emp.getEmployee().getEid());
			  
			  if(!user.getPassword().equals("")) { 
			  userService.findByUserName(emp.getUserName()).setPassword(passwordEncoder.
			  encode(user.getPassword()));
			  
			  }
		 
			  
		empRepo.updateEmployeeById(emp.getEmployee().getEid(),user.getContactNo(),user.getFullName());
		
		System.out.println(empRepo.getEmployeeById(1L).getName());
		
		model.addAttribute("success","Successfully Updated");
		return "edit-employee-profile";
	}

}
