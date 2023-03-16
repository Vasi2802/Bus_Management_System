package org.antwalk.repository;

import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepo extends JpaRepository<Bus, Long>{

    public List<Bus> findAllByR(Route route);
    
    public Optional<Bus> findByD(Driver driver);

    public void deleteByR(Route route);
    
}
