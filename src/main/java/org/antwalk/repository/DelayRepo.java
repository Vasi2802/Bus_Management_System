package org.antwalk.repository;

import java.time.LocalTime;

import org.antwalk.entity.Delay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelayRepo extends JpaRepository<Delay, Long>{

    // public void flush();
    // public Delay addDelay(long bid, LocalTime time);
    // public LocalTime getDelay(long bid);

}
