package org.antwalk.entity;

import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "time")
public class ArrivalTimeTable {

	@EmbeddedId
	private RouteStopId routeStopId;

	@Column(name = "morning_arrival_time")
	@NotNull
	private LocalTime morningArrivalTime;

	@Column(name = "evening_arrival_time")
	@NotNull
	private LocalTime eveningArrivalTime;

	public ArrivalTimeTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrivalTimeTable(RouteStopId routeStopId, LocalTime morningArrivalTime, LocalTime eveningArrivalTime) {
		this.routeStopId = routeStopId;
		this.morningArrivalTime = morningArrivalTime;
		this.eveningArrivalTime = eveningArrivalTime;
	}

	public RouteStopId getRouteStopId() {
		return routeStopId;
	}

	public void setRouteStopId(RouteStopId routeStopId) {
		this.routeStopId = routeStopId;
	}

	public LocalTime getMorningArrivalTime() {
		return morningArrivalTime;
	}

	public void setMorningArrivalTime(LocalTime morningArrivalTime) {
		this.morningArrivalTime = morningArrivalTime;
	}

	public LocalTime getEveningArrivalTime() {
		return eveningArrivalTime;
	}

	public void setEveningArrivalTime(LocalTime eveningArrivalTime) {
		this.eveningArrivalTime = eveningArrivalTime;
	}

	public LocalTime getCurrentTime() {
		LocalTime currentTime = LocalTime.now();
        LocalTime onePM = LocalTime.of(13, 0); // 1 PM
        
        if (currentTime.isAfter(onePM)) {
            return eveningArrivalTime;
        } else {
        	return morningArrivalTime;
        }
    }
	}


