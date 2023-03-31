package org.antwalk.service;

import java.security.Principal;

import org.antwalk.entity.User;
import org.antwalk.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	public void save(CrmUser crmUser);

//	public User findByEmail(String email);
	
	 public boolean canUpdateEmployee(Principal principal, Long id);

	public void savedriver(CrmUser theCrmUser);

	
}
