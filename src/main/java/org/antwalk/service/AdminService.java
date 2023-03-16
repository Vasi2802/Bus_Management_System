package org.antwalk.service;

import java.util.List;

import org.antwalk.entity.Admin;
import org.antwalk.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AdminService {

	@Autowired
	private AdminRepo adminRepo;
	
	public Admin insertAdmin(Admin a) {
		return adminRepo.save(a);
	}
	
	public List<Admin> getAllAdmin(){
		return adminRepo.findAll();
	}
	
	public Admin getAdminById(long id) {
		return adminRepo.findById(id).get();
	}
	
	public String deleteAdminById(long id) {
		adminRepo.deleteById(id);
		return "Admin Deleted";
	}
	
	public String updateAdminById(Admin a, long id) {
		List<Admin> adminList = adminRepo.findAll();
		for(Admin obj:adminList) {
			if(obj.getAid() == id) {
				if(a.getAid() == id) {
					adminRepo.save(a);
					return "Admin Updated";
				}
				
				else {
					return "Admin exists but your input id does not match with the existing Admin id";
				}
				
			}
		}
		return "Admin does not exist";
		
	}
}
