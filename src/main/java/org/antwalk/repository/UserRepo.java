package org.antwalk.repository;

import java.util.Optional;


import org.antwalk.entity.Employee;

import org.antwalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findById(Long id);
	
	
//    public void deleteByEmployee(Employee employee);

}
