package org.antwalk.repository;


import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Bus;

import org.antwalk.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{


    List<Employee> findAllByB(Bus bus);

    Optional<Employee> findByEid(long employeeId);

}
