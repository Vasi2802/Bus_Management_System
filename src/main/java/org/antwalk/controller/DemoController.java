package org.antwalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

	@GetMapping("/")
	public String showLogin() {
		
		return "login";
	}
	
	@GetMapping("/driver")
	public String showHome() {
		
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
	
	@GetMapping("/driver/preview")
	public String showDriverpreview() {
		
		return "preview";
	}
	
}










