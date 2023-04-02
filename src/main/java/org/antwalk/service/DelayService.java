package org.antwalk.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.Bus;
import org.antwalk.entity.Delay;
import org.antwalk.repository.ArrivalTimeRepo;
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
	private ArrivalTimeRepo arrivalTimeRepo;

	@Autowired
	private ArrivalTimeService arrivalTimeService;

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

	public List<Delay> getLatestList(long bid){
		List<Delay> retVal = new ArrayList<Delay>();
		for(Delay d: delayRepo.findAll()){
			if(d.getBus().getBid() == bid) {
					retVal.add(d);
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

	public LocalTime addTime(LocalTime time1, LocalTime time2){
		LocalTime retVal = time1.plusSeconds(time2.getSecond());
		retVal = retVal.plusMinutes(time2.getMinute());
		retVal = retVal.plusHours(time2.getHour());
		return retVal;
	}

	public LocalTime substractTime(LocalTime time1 , LocalTime time2){
		LocalTime retVal = time1.minusSeconds(time2.getSecond());
		retVal = retVal.minusMinutes(time2.getMinute());
		retVal = retVal.minusHours(time2.getHour());
		return retVal;
	}

	// public List<HashMap<String,String>> getAdjustedTimes(long bid,int slotIdx) {
	// 	List<HashMap<String,String>> retVal = new ArrayList<>();
	// 	// List<LocalTime> retVal = new ArrayList<LocalTime>() ;
	// 	Bus bus = busServices.getBusById(bid);
	// 	List<ArrivalTimeTable> ats ;
	// 	if(slotIdx == 0){ ats = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByMorningArrivalTime(bus.getR());}
	// 	else{ats = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByEveningArrivalTime(bus.getR());}
	// 	List<Delay> delays = delayRepo.findByBusOrderByActualTime(bus);
	// 	LocalTime delta = LocalTime.parse("00:00:00");
	// 	LocalTime temp;
	// 	if(delays.size() > 0 ) {
	// 		temp = delays.get(0).getActualTime();
	// 		if(slotIdx == 0 ) {
	// 			delta = substractTime(temp, bus.getStartTime());
	// 		}
	// 		else{
	// 			delta = substractTime(temp, LocalTime.parse("17:30:00"));
	// 		}
	// 	}
	// 	int idx = 0;
	// 	int delaysSize = delays.size();
	// 	while(true){
	// 		// System.out.println("delta  = " +  delta);
	// 		if(idx >= delaysSize){
	// 			if(idx >= delays.size() + ats.size()){
	// 				break;
	// 			}
	// 			else{
	// 				ArrivalTimeTable at =ats.get(idx - delays.size());
	// 				if(slotIdx == 0){temp = at.getMorningArrivalTime();}
	// 				else{temp = at.getEveningArrivalTime();}
	// 				temp = addTime(delta,temp);
	// 				String stopName = at.getRouteStopId().getStop().getName();
	// 				System.out.println("index= " + (idx - delaysSize ) + "from delay table= " + retVal.get(idx - delaysSize ).get("name") + "current= " + stopName);
	// 				if(retVal.size() > idx - delaysSize  && !retVal.get(idx - delaysSize ).get("name").equals(stopName)){ 
	// 					HashMap<String,String> val = new HashMap<>();
	// 					val.put("name",stopName);
	// 					val.put("time",String.valueOf(temp));
	// 					retVal.add(val);
	// 				}
	// 			}
 	// 		}
	// 		else {
	// 			if(idx > 0){
	// 				Delay d = delays.get(idx);
	// 				temp = d.getActualTime();
	// 				delta = substractTime(delta,temp);
	// 				HashMap<String,String> val = new HashMap<>();
	// 				val.put("name", d.getStop().getName());
	// 				val.put("time",String.valueOf(temp));
	// 				retVal.add(val);
	// 			}
	// 		}
	// 		idx += 1;
	// 	}
	// 	// System.out.println(retVal);
	// 	return retVal;
	// }


	public List<HashMap<String,String>> getAdjustedTimes(long bid,int slotIdx) {
		List<HashMap<String,String>> retVal = new ArrayList<>();
		// List<LocalTime> retVal = new ArrayList<LocalTime>() ;
		Bus bus = busServices.getBusById(bid);
		List<ArrivalTimeTable> ats ;
		if(slotIdx == 0){ ats = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByMorningArrivalTime(bus.getR());}
		else{ats = arrivalTimeRepo.findAllByRouteStopId_RouteOrderByEveningArrivalTime(bus.getR());}
		List<Delay> delays = delayRepo.findByBusOrderByActualTime(bus);
		LocalTime delta = LocalTime.parse("00:00:00");
		LocalTime temp;
		if(delays.size() > 0 ) {
			temp = delays.get(0).getActualTime();
			if(slotIdx == 0 ) {
				delta = substractTime(temp, bus.getStartTime());
			}
			else{
				delta = substractTime(temp, LocalTime.parse("17:30:00"));
			}
		} 
		LocalTime actTime, expTime;
		for(int i = 0; i < delays.size();i++){
			Delay d = delays.get(i) ; 
			if(d.getStop()!= null) {
				if(i == delays.size() - 1){
					actTime = d.getActualTime();
					if(slotIdx == 0 ){
						expTime = arrivalTimeService.getArrivalTimeById(bus.getR().getRid(),d.getStop().getSid()).getMorningArrivalTime();
					}
					else{
						expTime = arrivalTimeService.getArrivalTimeById(bus.getR().getRid(),d.getStop().getSid()).getEveningArrivalTime();
					}
					delta = substractTime(actTime, expTime);
				}
				HashMap<String,String> val = new HashMap<>() ;
				val.put("name",d.getStop().getName());
				val.put("time",String.valueOf(d.getActualTime()));
				retVal.add(val);
			}
		}
		int idx = 0;
		LocalTime temp1;
		Boolean toAdd;
		System.out.println(ats);
		for(ArrivalTimeTable at: ats) {
			if(idx < retVal.size()) {
				System.out.println("from delay = " + retVal.get(idx).get("name") + " from arrival = " +at.getRouteStopId().getStop().getName());
			}
			toAdd = true;
			if(retVal.size() > idx  && retVal.get(idx).get("name").equals(at.getRouteStopId().getStop().getName())){toAdd = false;}
			if(toAdd) {
				if(slotIdx == 0 ){
					temp1 = at.getMorningArrivalTime();
				}
				else{
					temp1 = at.getEveningArrivalTime();
				}
				actTime = addTime(delta,temp1);
				HashMap<String,String> val = new HashMap<>() ;
				val.put("name",at.getRouteStopId().getStop().getName());
				// if(actTime.isBefore(LocalTime.now())) {val.put("time","--:--:--");}
				// else{val.put("time",String.valueOf(actTime));}
				val.put("time",String.valueOf(actTime));
				retVal.add(val);
			}
			idx  += 1;
		}
		// System.out.println(retVal);
		return retVal;
	}



	public List<Delay> getNullStopDelays()
	{
		System.out.println(delayRepo.findByStop(null));
		return delayRepo.findByStop(null) ;

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
