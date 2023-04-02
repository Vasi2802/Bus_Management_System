package org.antwalk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorWebController implements ErrorController {

  @GetMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    // Determine the HTTP status code of the error
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

    // Set the status code in the model
    model.addAttribute("statusCode", statusCode);

    // Return the name of the Thymeleaf template to use for this error
    return "error1";
  }

  public String getErrorPath() {
    return "/error";
  }
}