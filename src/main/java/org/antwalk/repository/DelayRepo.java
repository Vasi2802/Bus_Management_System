package org.antwalk.repository;

import java.time.LocalTime;
import java.util.List;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Delay;
import org.antwalk.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelayRepo extends JpaRepository<Delay, Long>{

    // public void flush();
    // public Delay addDelay(long bid, LocalTime time);
    // public LocalTime getDelay(long bid);

    public List<Delay> findByBusOrderByActualTime(Bus bus);
    public List<Delay> findByStop(Stop stop);

}
