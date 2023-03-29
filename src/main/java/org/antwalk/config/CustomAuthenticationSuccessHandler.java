package org.antwalk.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.antwalk.entity.User;
import org.antwalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");

		String userName = authentication.getName();
		
	
		System.out.println("userName=" + userName);

		User theUser = userService.findByUserName(userName);
		
		String role =theUser.getRole();
		
		// now place in the session
		if(role.equals("ROLE_ADMIN")) {
		HttpSession session = request.getSession();
		session.setAttribute("admin", theUser);
		
		// forward to home page
		
		response.sendRedirect(request.getContextPath() + "/admin");
		}
		else if(role.equals("ROLE_EMPLOYEE")) {
			HttpSession session = request.getSession();
			session.setAttribute("emp", theUser);
			// forward to home page
			
			response.sendRedirect(request.getContextPath() + "/employee");
			}
		
		else if(role.equals("ROLE_DRIVER")) {
			HttpSession session = request.getSession();
			session.setAttribute("driver", theUser);
			
			// forward to home page
			
			response.sendRedirect(request.getContextPath() + "/driver");
			}
		}
	
		
	}

