package com.revature.dtos;

import java.util.Objects;

public class NewInterviewData {
  	private String associateEmail;
  	private String managerEmail;
	private long date;
	private String location;
	private String client; 

	public NewInterviewData() {
		super();
	}

	public NewInterviewData(String associateEmail, String managerEmail, long date, String location, String client) {
		super();
		this.associateEmail = associateEmail;
		this.managerEmail = managerEmail;
		this.date = date;
		this.location = location;
		this.client = client;
	}

	public String getAssociateEmail() {
		return this.associateEmail;
	}

	public void setAssociateEmail(String associateEmail) {
		this.associateEmail = associateEmail;
	}

	public long getDate() {
		return this.date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public NewInterviewData associateEmail(String associateEmail) {
		this.associateEmail = associateEmail;
		return this;
	}

	public NewInterviewData date(long date) {
		this.date = date;
		return this;
	}

	public NewInterviewData location(String location) {
		this.location = location;
		return this;
	}

	public NewInterviewData client(String client) {
		this.client = client;
		return this;
	}
	
	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	@Override
	public String toString() {
		return "NewInterviewData [associateEmail=" + associateEmail + ", managerEmail=" + managerEmail + ", date="
				+ date + ", location=" + location + ", client=" + client + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associateEmail == null) ? 0 : associateEmail.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + (int) (date ^ (date >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((managerEmail == null) ? 0 : managerEmail.hashCode());
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
		NewInterviewData other = (NewInterviewData) obj;
		if (associateEmail == null) {
			if (other.associateEmail != null)
				return false;
		} else if (!associateEmail.equals(other.associateEmail))
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (date != other.date)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (managerEmail == null) {
			if (other.managerEmail != null)
				return false;
		} else if (!managerEmail.equals(other.managerEmail))
			return false;
		return true;
	}
	
	

	
}
