package org.antwalk.repository;

import java.util.List;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrivalTimeRepo extends JpaRepository<ArrivalTimeTable, RouteStopId>{

    public List<ArrivalTimeTable> findAllByRouteStopId_Stop(Stop stop);
    
    public List<ArrivalTimeTable> findAllByRouteStopId_RouteOrderByMorningArrivalTime(Route route);

    public List<ArrivalTimeTable> findAllByRouteStopId_RouteOrderByEveningArrivalTime(Route route);

}
