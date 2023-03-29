package org.antwalk.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// NOT AN ENTITY

@Embeddable
public class RouteStopId implements Serializable {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "route_id", referencedColumnName = "rid")
	private Route route;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stop_id", referencedColumnName = "sid")
	private Stop stop;

	public RouteStopId() {
	}


	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result + ((stop == null) ? 0 : stop.hashCode());
		return result;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RouteStopId other = (RouteStopId) obj;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (stop == null) {
			if (other.stop != null)
				return false;
		} else if (!stop.equals(other.stop))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RouteStop [route=" + route + ", stop=" + stop + "]";
	}

	public RouteStopId(Route route, Stop stop) {
		super();
		this.route = route;
		this.stop = stop;
	}
	
	
	
	

}
