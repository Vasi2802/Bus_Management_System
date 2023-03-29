package org.antwalk.service;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Delay;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.DelayRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.twilio.twiml.voice.Stop;

@Service
public class DelayService {

	@Autowired
	private DelayRepo delayRepo;

    @Autowired
	private BusService busServices;

	@Autowired
	private EntityManager entityManager;

    public void flush(){
        delayRepo.deleteAll();
    }

    // public Delay addDelay(long bid, LocalTime time){
    //     return delayRepo.save(new Delay(bid,time));
    // }

	public Delay addDelay(Delay d){
        return delayRepo.save(d);
    }

	public LocalTime getDelay(long bid, long sid){
		for(Delay d: delayRepo.findAll()){
			if(d.getBus().getBid() == bid && d.getStop().getSid() == sid ){
				return d.getActualTime();
			}
		}
		return null;
	}

	public Delay getLatest(long bid){
		Delay retVal = null;
		for(Delay d: delayRepo.findAll()){
			if(d.getBus().getBid() == bid) {
				if(retVal == null || retVal.getActualTime().compareTo(d.getActualTime()) < 0){
					retVal = d;
				}
			}
		}
		return retVal;
	}

	public ArrivalTimeTable getNextStop(long bid,Delay lastStop,int slotIdx){
		LocalTime stopTime ;
		Delay d = lastStop;
		// can be two cases
			// case 1 : d is null : no stop of this route in table ; handled in controller
			// case 2 : d.getStop() is null : only depot has been added to table
			// case 3 : d.getStop() != null
		long rid = busServices.getBusById(bid).getR().getRid();
		if(d.getStop() != null){
			long sid = d.getStop().getSid();
			List<ArrivalTimeTable> temp = entityManager.createNativeQuery("select * from time where route_id = " + rid +" and stop_id= " +sid,ArrivalTimeTable.class).getResultList();
			if(slotIdx == 0){
				stopTime = temp.get(0).getMorningArrivalTime();
			}
			else {
				stopTime = temp.get(0).getEveningArrivalTime();
			}
		}
		else{stopTime = LocalTime.MIN;}

		String slot = "";
		if(slotIdx == 0) {slot += "morning";}
		else{  slot += "evening";}
		slot += "_arrival_time";
		String qry = "select * from time where route_id = " + rid + " and "+slot + " > '" + stopTime + "' order by " + slot + " asc";
		System.out.println(qry);
		List<ArrivalTimeTable> routeStops = entityManager.createNativeQuery(qry ,ArrivalTimeTable.class).getResultList();
		if(routeStops.size() > 0) {
			System.out.println(routeStops.get(0).getRouteStopId().getStop().getName());
			return routeStops.get(0);
		}
		System.out.println("no more stops found");
		return null;
	}


	@Transactional
	public String flushByBusId(long bid){
		entityManager.createNativeQuery("delete from delay where bus_id= " + bid).executeUpdate();
		return "flushed";
	}

	public String getDelayStatus(long bid) {
		String qry =  "select * from delay where bus_id = " + bid + " order by actual_time asc";
		System.out.println(qry);
		List<Delay> delays =  entityManager.createNativeQuery(qry, Delay.class ).getResultList();
		if(delays.size() == 0) {
			return "Not yet Started";
		}

		LocalTime exStarTime = LocalTime.parse("17:00:00");
		if(LocalTime.now().compareTo(LocalTime.parse("12:00:00")) < 0 ){
			exStarTime = busServices.getBusById(bid).getStartTime();
		}
		if(delays.get(0).getActualTime().compareTo(exStarTime) != 0) {
			return "started by driver at " + String.valueOf(delays.get(0).getActualTime()) ;
		}
		return "on time";
		// return "started by driver at " + String.valueOf(delays.get(0).getActualTime()) ;
	}

	

	





	
	// public LocalTime getDelay(long bid) {
	// 	for(Delay obj: delayRepo.findAll()) {
	// 		if(obj.getBid()  == bid) {
    //             return obj.getDelayedby();
	// 		}
	// 	}
	// 	return busServices.getBusById(bid).getStartTime();
	// }
}
