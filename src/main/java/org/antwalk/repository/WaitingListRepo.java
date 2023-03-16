package org.antwalk.repository;

import java.util.List;
import java.util.Optional;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Employee;
import org.antwalk.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WaitingListRepo extends JpaRepository<WaitingList, Long>{

    public List<WaitingList> findAllByE(Employee employee);
    
    public Optional<WaitingList> findByE(Employee employee);

    public Long deleteByE(Employee employee);

    public List<WaitingList> findAllByBOrderByWid(Bus bus);

    @Query("select count(*) from WaitingList wl where wl.b in (?1)")
    public long countByBus(List<Bus> b);

    public List<WaitingList> findByBIn(List<Bus> b);

    public List<WaitingList> findAllByB(Bus bus);

    public void deleteAllByB(Bus bus);
    
}
