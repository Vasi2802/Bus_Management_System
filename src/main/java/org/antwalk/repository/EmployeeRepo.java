package org.antwalk.repository;

import java.util.List;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{

    List<Employee> findAllByB(Bus bus);


}
