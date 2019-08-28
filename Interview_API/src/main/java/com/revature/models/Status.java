package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "status")
public class Status {


	@Id
	@Column(name = "status_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statusId;

	@Column(name = "general_status")
	private String generalStatus;

	@Column(name = "specific_status")
	private String specificStatus;

	@Column(name = "virtual")
	private boolean virtual;

	public int getStatusId() {
		return statusId;
	}



	public String getGeneralStatus() {
		return generalStatus;
	}



	public String getSpecificStatus() {
		return specificStatus;
	}



	public boolean isVirtual() {
		return virtual;
	}



	public Status() {
		super();
	}

	public Status(int statusId, String generalStatus, String specificStatus, boolean virtual) {
		super();
		this.statusId = statusId;
		this.generalStatus = generalStatus;
		this.specificStatus = specificStatus;
		this.virtual = virtual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((generalStatus == null) ? 0 : generalStatus.hashCode());
		result = prime * result + ((specificStatus == null) ? 0 : specificStatus.hashCode());
		result = prime * result + statusId;
		result = prime * result + (virtual ? 1231 : 1237);
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
		Status other = (Status) obj;
		if (generalStatus == null) {
			if (other.generalStatus != null)
				return false;
		} else if (!generalStatus.equals(other.generalStatus))
			return false;
		if (specificStatus == null) {
			if (other.specificStatus != null)
				return false;
		} else if (!specificStatus.equals(other.specificStatus))
			return false;
		if (statusId != other.statusId)
			return false;
		if (virtual != other.virtual)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Status [statusId=" + statusId + ", generalStatus=" + generalStatus + ", specificStatus="
				+ specificStatus + ", virtual=" + virtual + "]";
	}	
}