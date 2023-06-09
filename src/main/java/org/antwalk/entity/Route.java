package org.antwalk.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="route")
public class Route {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rid")
	private long rid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "start", referencedColumnName = "sid")
	@NotNull
	private Stop start;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "end", referencedColumnName = "sid")
	@NotNull
	private Stop end;
	

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public Stop getStart() {
		return start;
	}

	public void setStart(Stop start) {
		this.start = start;
	}

	public Stop getEnd() {
		return end;
	}

	public void setEnd(Stop end) {
		this.end = end;
	}


	public Route(long rid, Stop start, Stop end) {

		super();
		this.rid = rid;
		this.start = start;
		this.end = end;
	}

	public Route() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Route(Stop start, Stop end) {

		super();
		this.start = start;
		this.end = end;
	}


	
	
}
