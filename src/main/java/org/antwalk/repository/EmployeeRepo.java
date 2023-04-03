package org.antwalk.repository;


import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Bus;

import org.antwalk.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{


    public List<Employee> findAllByB(Bus bus);

    public Optional<Employee> findByEid(long employeeId);

}
