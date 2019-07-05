package com.revature.models;

import java.sql.Timestamp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "status_history")
public class StatusHistory {

	@Id
	@Column(name = "status_history_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statusHistoryId;


	@Generated(GenerationTime.INSERT)
	@Column(name = "status_start", insertable = false)
	private Timestamp statusStart;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;


	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	public int getStatusHistoryId() {
		return statusHistoryId;
	}

	public void setStatusHistoryId(int statusHistoryId) {
		this.statusHistoryId = statusHistoryId;
	}

	public Timestamp getStatusStart() {
		return statusStart;
	}

	public void setStatusStart(Timestamp statusStart) {
		this.statusStart = statusStart;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public StatusHistory() {
		super();
	}


	public StatusHistory(int statusHistoryId, Timestamp statusStart, User user, Status status, Address address) {
		super();
		this.statusHistoryId = statusHistoryId;
		this.statusStart = statusStart;
		this.user = user;
		this.status = status;
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + statusHistoryId;
		result = prime * result + ((statusStart == null) ? 0 : statusStart.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		StatusHistory other = (StatusHistory) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusHistoryId != other.statusHistoryId)
			return false;
		if (statusStart == null) {
			if (other.statusStart != null)
				return false;
		} else if (!statusStart.equals(other.statusStart))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatusHistory [statusHistoryId=" + statusHistoryId + ", statusStart=" + statusStart + ", user=" + user
				+ ", status=" + status + ", address=" + address + "]";
	}

}