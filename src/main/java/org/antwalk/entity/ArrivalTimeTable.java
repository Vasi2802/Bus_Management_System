package org.antwalk.entity;

import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "time")
public class ArrivalTimeTable {

	@EmbeddedId
	private RouteStopId routeStopId;

	@Column(name = "morning_arrival_time")
	private LocalTime morningArrivalTime;

	@Column(name = "evening_arrival_time")
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

	@Override
	public int hashCode() {
		return Objects.hash(eveningArrivalTime, morningArrivalTime, routeStopId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrivalTimeTable other = (ArrivalTimeTable) obj;
		return Objects.equals(eveningArrivalTime, other.eveningArrivalTime)
				&& Objects.equals(morningArrivalTime, other.morningArrivalTime)
				&& Objects.equals(routeStopId, other.routeStopId);
	}
	
	
}
