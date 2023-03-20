package org.antwalk.repository;

import java.util.List;

import org.antwalk.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepo extends JpaRepository<Stop, Long>{

	public Stop findByName(String name);

}
