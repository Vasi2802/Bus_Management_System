package org.antwalk.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.antwalk.entity.GlobalDb;
import org.antwalk.entity.Otp;
import org.antwalk.entity.User;
import org.antwalk.service.GlobalDbService;
import org.antwalk.service.OtpService;
import org.antwalk.service.UserService;
import org.antwalk.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    @Autowired
    private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private GlobalDbService gb;
	
    @Autowired
    private OtpService otpService;
    
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@Autowired
	OtpService optserv;
	
	/*
	 * @GetMapping("/verifyemail") public String
	 * verifyotpval(@RequestParam("inputText") String input) {
	 * System.out.println(input); // System.out.println(email);
	 * 
	 * // Otp otpval = optserv.getOtpByEmail(email);
	 * 
	 * if(input.equals(otpval.getOtpValue())) { String pass = "NRIFINTECH";
	 * userService.findByUserName(email).setPassword(passwordEncoder.encode(pass));
	 * optserv.deleteOtpByEmail(email);
	 * model.addAttribute("successreset","Password has been reset successfully");
	 * return "redirect:/"; }
	 * 
	 * model.addAttribute("failurereset","Encountered error"); return
	 * "reset-password-get-email";
	 * 
	 * }
	 */
	
	@PostMapping("/verifyotp")
	public String verifyotpval(@RequestParam("email1") String email1,@RequestParam("otp") String otp,Model model ) {
		System.out.println(email1);
		System.out.println("OTP" +otp);
		
		
		 Otp otpval = optserv.getOtpByEmail(email1);
		  
		  if(otp.equals(otpval.getOtpValue())) { 
			  
//		  model.addAttribute("success","Password has been reset successfully"); 
		  model.addAttribute("email_ver",email1); 
		  
		  return "change-password-page"; 
	}
		
		model.addAttribute("failurereset","Encountered error");
		return "verification-page";
		
	}
	@PostMapping("/verifypassword")
	public String verifypassword(@RequestParam("email_final") String email,@RequestParam("pass1") String pass1,@RequestParam("pass2") String pass2,Model model ) {
		System.out.println("HELLO"+email+pass1+pass2);
		
		
		if(!pass1.equals(pass2)) {
			model.addAttribute("notmatch","Password does not match");
			return "change-password-page";
		}
		
		userService.findByUserName(email).setPassword(passwordEncoder.encode(pass1));
		optserv.deleteOtpByEmail(email);
		model.addAttribute("successresetpassword","Password does not match");
		return "login";
		
	}
	//$2a$10$SeTGQXSvA3vlLOq9OQwrKevvzSJ/AmvUxyusyrlDBIybiQolY8teS
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("crmUser", new CrmUser());
		
		return "registration-form.html";
	}

	@GetMapping("/resetpassword")
	public String showResetPage() {
		
		return "reset-password-get-email";
	}
	
	@PostMapping("/verifyemail")
	public String verifyotp(@RequestParam("email") String email,Model model) {
			User existing = userService.findByUserName(email);
        if (existing == null){
        	System.out.println("Exists");
//			logger.warning("Email already exists.");
			model.addAttribute("error","Email exists");
        	return "reset-password-get-email";
        }
        
//        model.addAttribute("verify","Check for verification");
        model.addAttribute("email2",email);
        otpService.generateOtp(email);
        
		return "verification-page";
		
	}
//	@PostMapping("/verifyotpval")
//	public String verifyotpval(@RequestBody String input) {
//		
//		return "login";
//		
//	}
//	
//	@PostMapping("/verifyreceivedotp")
//	public String verifyotp1(@RequestParam("email") String email,Model model) {
//	
//	}
	

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		String userName = theCrmUser.getEmail();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "registration-form.html";
	        }

		// check the database if user already exists
        User existing = userService.findByUserName(userName);
        
        if (existing != null){
        	theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "registration-form";
        }
		
      
        
        
        logger.info("Successfully created user: " + userName);
        
        
        List<GlobalDb>  all= gb.getAllAdmin();
        
        Boolean exist =false;
        for(GlobalDb gb : all) {
        	if(gb.getEmail().equals(userName)) {
        		exist = true;
        		break;
        	}
        		
        }
        if(exist==true) {
        	theModel.addAttribute("notauser", "Invalid email");

        	return "registration-form";
        }
        theModel.addAttribute("confirmregister","Successfully Registered");
        userService.save(theCrmUser);
        return "login";
       
	}
}
