package org.antwalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class EmployeeWebController {
	@RequestMapping("errorupdate")
	public String getById() {
		return "error1.html";
	}
	
}