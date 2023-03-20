package org.antwalk.service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.antwalk.entity.Bus;
import org.antwalk.entity.Driver;
import org.antwalk.entity.Route;
import org.antwalk.repository.BusRepo;
import org.antwalk.repository.DriverRepo;
import org.antwalk.repository.RouteRepo;
import org.aspectj.apache.bcel.util.ByteSequence;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BusService {

	@Autowired
	private BusRepo busRepo;
	
	@Autowired
	private RouteRepo routeRepo;

	@Autowired
	private DriverRepo driverRepo;
	

	public Bus insertBus(Bus b) {
		return busRepo.save(b);
	}

	public List<Bus> getAllBus() {
		return busRepo.findAll();
	}

	public Bus getBusById(long id) {
		return busRepo.findById(id).get();
	}
	public String busStatus(long id) {
		LocalTime res = busRepo.getById(id).getStartTime();
		
		if(res!=null)
			return res.toString();
		else
			return "Journey not Started";
	}
	

	public String deleteBusById(long id) {
		busRepo.deleteById(id);
		return "Bus Deleted";
	}

	public String updateBusById(Bus b, long id) {
		List<Bus> busList = busRepo.findAll();
		for (Bus obj : busList) {
			if (obj.getBid() == id) {
				if (b.getBid() == id) {
					busRepo.save(b);
					return "Bus Updated";
				}

				else {
					return "Bus exists but your input id does not match with the existing Bus id";
				}

			}
		}
		return "Bus does not exist";

	}

	// GET TOTAL NO. OF PASSENGERS PER BUS
	/*
	 * Returns Map<Bus, PassengersCount> as Map<Bus, Integer>
	 * 
	 * Arguments
	 * None
	 */

	public Map<Bus, Integer> getPassesngersPerBus() {
		Map<Bus, Integer> passengerFreq = new HashMap<>();
		List<Bus> buses = busRepo.findAll();
		for (Bus bus : buses) {
			passengerFreq.put(bus, bus.getTotalSeats() - bus.getAvailableSeats());
		}
		return passengerFreq;
	}

	public int getTotalSeatsOfAll() {
		List<Bus> buses = busRepo.findAll();
		int total = 0;
		for (Bus bus : buses) {
			total += bus.getTotalSeats();
		}
		return total;
	}

	public int getTotalBookedSeats() {
		List<Bus> buses = busRepo.findAll();
		int booked = 0;
		for (Bus bus : buses) {
			booked += bus.getTotalSeats() - bus.getAvailableSeats();
		}
		return booked;
	}

	public long getMostUsedBus() {
		List<Bus> buses = busRepo.findAll();
		int booked = 0;
		Bus maxUsedBus = null;
		int max = 0;
		for (Bus bus : buses) {
			booked = bus.getTotalSeats() - bus.getAvailableSeats();
			if (booked >= max) {
				max = booked;
				maxUsedBus = bus;
			}

		}
		if (maxUsedBus == null) {
			return 0;
		}
		return maxUsedBus.getBid();
	}

	public int getMostUsedRoute() {
		List<Bus> buses = busRepo.findAll();
		int booked = 0;
		Bus maxUsedBus = null;
		int max = 0;
		for (Bus bus : buses) {
			booked = bus.getTotalSeats() - bus.getAvailableSeats();
			if (booked >= max) {
				max = booked;
				maxUsedBus = bus;
			}

		}
		if (maxUsedBus == null) {
			return 0;
		}
		return (int)maxUsedBus.getR().getRid();
	}

    public Bus addBus(int totalSeats, long routeId, long driverId) {
        Optional<Route> routeOptional = routeRepo.findById(routeId);
		Optional<Driver> driverOptional = driverRepo.findById(driverId);
		if(routeOptional.isEmpty() || driverOptional.isEmpty()){
			return null;
		}
		Route route = routeOptional.get();
		Driver driver = driverOptional.get();
		Bus bus = new Bus(totalSeats, totalSeats, null, driver, route);
		busRepo.save(bus);
		return bus;
    }

}
