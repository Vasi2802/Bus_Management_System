package org.antwalk.service;

import java.util.List;

import org.antwalk.entity.Admin;
import org.antwalk.entity.GlobalDb;
import org.antwalk.repository.GlobalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalDbService {

	@Autowired
	private GlobalRepo globalRepo;
	
	public List<GlobalDb> getAllAdmin(){
		return globalRepo.findAll();
	}

}
