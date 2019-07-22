package com.revature.dtos;

import java.util.Date;

import com.revature.models.Interview;

public class FeedbackStat {

	private int id;
	private String managerEmail;
	private String associateEmail;
	private Date feedbackRequested;
	private Date feedbackRecieved;
	private Date feedbackDelivered;
	private String managerName;
	private String associateName;
	
	public FeedbackStat(Interview interview, String managerName, String associateName) {
		super();
		this.id = interview.getId();
		this.managerEmail = interview.getManagerEmail();
		this.associateEmail = interview.getAssociateEmail();
		this.feedbackRequested = interview.getFeedback().getFeedbackRequested();
		this.feedbackRecieved = interview.getFeedback().getFeedbackReceived();
		this.feedbackDelivered = interview.getFeedback().getFeedbackDelivered();
		this.managerName = managerName;
		this.associateName = associateName;
	}
	
	public FeedbackStat() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getAssociateEmail() {
		return associateEmail;
	}

	public void setAssociateEmail(String associateEmail) {
		this.associateEmail = associateEmail;
	}

	public Date getFeedbackRequested() {
		return feedbackRequested;
	}

	public void setFeedbackRequested(Date feedbackRequested) {
		this.feedbackRequested = feedbackRequested;
	}

	public Date getFeedbackRecieved() {
		return feedbackRecieved;
	}

	public void setFeedbackRecieved(Date feedbackRecieved) {
		this.feedbackRecieved = feedbackRecieved;
	}

	public Date getFeedbackDelivered() {
		return feedbackDelivered;
	}

	public void setFeedbackDelivered(Date feedbackDelivered) {
		this.feedbackDelivered = feedbackDelivered;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	@Override
	public String toString() {
		return "FeedbackStat [id=" + id + ", managerEmail=" + managerEmail + ", associateEmail=" + associateEmail
				+ ", feedbackRequested=" + feedbackRequested + ", feedbackRecieved=" + feedbackRecieved
				+ ", feedbackDelivered=" + feedbackDelivered + ", managerName=" + managerName + ", associateName="
				+ associateName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associateEmail == null) ? 0 : associateEmail.hashCode());
		result = prime * result + ((associateName == null) ? 0 : associateName.hashCode());
		result = prime * result + ((feedbackDelivered == null) ? 0 : feedbackDelivered.hashCode());
		result = prime * result + ((feedbackRecieved == null) ? 0 : feedbackRecieved.hashCode());
		result = prime * result + ((feedbackRequested == null) ? 0 : feedbackRequested.hashCode());
		result = prime * result + id;
		result = prime * result + ((managerEmail == null) ? 0 : managerEmail.hashCode());
		result = prime * result + ((managerName == null) ? 0 : managerName.hashCode());
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
		FeedbackStat other = (FeedbackStat) obj;
		if (associateEmail == null) {
			if (other.associateEmail != null)
				return false;
		} else if (!associateEmail.equals(other.associateEmail))
			return false;
		if (associateName == null) {
			if (other.associateName != null)
				return false;
		} else if (!associateName.equals(other.associateName))
			return false;
		if (feedbackDelivered == null) {
			if (other.feedbackDelivered != null)
				return false;
		} else if (!feedbackDelivered.equals(other.feedbackDelivered))
			return false;
		if (feedbackRecieved == null) {
			if (other.feedbackRecieved != null)
				return false;
		} else if (!feedbackRecieved.equals(other.feedbackRecieved))
			return false;
		if (feedbackRequested == null) {
			if (other.feedbackRequested != null)
				return false;
		} else if (!feedbackRequested.equals(other.feedbackRequested))
			return false;
		if (id != other.id)
			return false;
		if (managerEmail == null) {
			if (other.managerEmail != null)
				return false;
		} else if (!managerEmail.equals(other.managerEmail))
			return false;
		if (managerName == null) {
			if (other.managerName != null)
				return false;
		} else if (!managerName.equals(other.managerName))
			return false;
		return true;
	}

	
	
}
