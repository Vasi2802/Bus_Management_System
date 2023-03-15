package org.antwalk.controller;

import javax.servlet.http.HttpServletRequest;
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

@Controller
public class DemoController {

	   @Autowired
	    private UserService userService;
		
		@Autowired
		private BCryptPasswordEncoder passwordEncoder;
		
		
		
	@GetMapping("/")
	public String showLogin() {
		
		return "login";
	}
	
	@GetMapping("/driver")
	public String showHome() {
		
		return "driver";
	}
	
	// add request mapping for /leaders

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
		
		model.addAttribute("defaultpass","NRIFINTECH");
		return "registration-form-driver";
	}
	
	@PostMapping("/admin/register/processRegistrationFormdriver")
	public String processRegistrationForm(
			@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
			BindingResult theBindingResult, 
			Model theModel) {
	
	String userName = theCrmUser.getEmail();
//	logger.info("Processing registration form for: " + userName);
	
	// form validation
	 if (theBindingResult.hasErrors()){
		 return "registration-form-driver";
        }

	// check the database if user already exists
    User existing = userService.findByUserName(userName);
    
    if (existing != null){
    	theModel.addAttribute("crmUser", new CrmUser());
		theModel.addAttribute("registrationError", "User name already exists.");

//		logger.warning("User name already exists.");
    	return "registration-form-driver";
    }     						
    userService.savedriver(theCrmUser);
    
//    logger.info("Successfully created user: " + userName);
    
    theModel.addAttribute("confirmregister","Successfully Registered");
    return "admin";		
}
	
	@GetMapping("/driver/preview")
	public String showDriverpreview() {
		
		return "preview";
	}
	


}










