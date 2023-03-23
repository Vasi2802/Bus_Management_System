package org.antwalk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="delay")
public class Delay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="delayid")
	private long delayid;
	
	@Column(name="bid")
	@NotNull
	private int bid;
	
	@Column(name="delayedby")
	@NotNull
	private int delayedby;

	public long getDelayid() {
		return delayid;
	}

	public void setDelayid(long delayid) {
		this.delayid = delayid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getDelayedby() {
		return delayedby;
	}

	public void setDelayedby(int delayedby) {
		this.delayedby = delayedby;
	}
}
