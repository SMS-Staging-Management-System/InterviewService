package com.revature.dtos;

public class FeedbackData{
	
	int interviewId;
    long feedbackRequestedDate;
    String feedbackText;
    long feedbackReceivedDate;
    long feedbackDeliveredDate;
//    String interviewFormat;
    int statusId;
    int format;
	public FeedbackData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FeedbackData(int interviewId, long feedbackRequestedDate, String feedbackText, long feedbackReceivedDate,
			long feedbackDeliveredDate, int statusId, int format) {
		super();
		this.interviewId = interviewId;
		this.feedbackRequestedDate = feedbackRequestedDate;
		this.feedbackText = feedbackText;
		this.feedbackReceivedDate = feedbackReceivedDate;
		this.feedbackDeliveredDate = feedbackDeliveredDate;
//		this.interviewFormat = interviewFormat;
		this.statusId = statusId;
		this.format = format;
	}
	public int getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}
	public long getFeedbackRequestedDate() {
		return feedbackRequestedDate;
	}
	public void setFeedbackRequestedDate(long feedbackRequestedDate) {
		this.feedbackRequestedDate = feedbackRequestedDate;
	}
	public String getFeedbackText() {
		return feedbackText;
	}
	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}
	public long getFeedbackReceivedDate() {
		return feedbackReceivedDate;
	}
	public void setFeedbackReceivedDate(long feedbackReceivedDate) {
		this.feedbackReceivedDate = feedbackReceivedDate;
	}
	public long getFeedbackDeliveredDate() {
		return feedbackDeliveredDate;
	}
	public void setFeedbackDeliveredDate(long feedbackDeliveredDate) {
		this.feedbackDeliveredDate = feedbackDeliveredDate;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getFormat() {
		return format;
	}
	public void setFormat(int format) {
		this.format = format;
	}
	@Override
	public String toString() {
		return "FeedbackData [interviewId=" + interviewId + ", feedbackRequestedDate=" + feedbackRequestedDate
				+ ", feedbackText=" + feedbackText + ", feedbackReceivedDate=" + feedbackReceivedDate
				+ ", feedbackDeliveredDate=" + feedbackDeliveredDate + ", statusId=" + statusId + ", format=" + format + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (feedbackDeliveredDate ^ (feedbackDeliveredDate >>> 32));
		result = prime * result + (int) (feedbackReceivedDate ^ (feedbackReceivedDate >>> 32));
		result = prime * result + (int) (feedbackRequestedDate ^ (feedbackRequestedDate >>> 32));
		result = prime * result + ((feedbackText == null) ? 0 : feedbackText.hashCode());
		result = prime * result + format;
		result = prime * result + interviewId;
		result = prime * result + statusId;
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
		FeedbackData other = (FeedbackData) obj;
		if (feedbackDeliveredDate != other.feedbackDeliveredDate)
			return false;
		if (feedbackReceivedDate != other.feedbackReceivedDate)
			return false;
		if (feedbackRequestedDate != other.feedbackRequestedDate)
			return false;
		if (feedbackText == null) {
			if (other.feedbackText != null)
				return false;
		} else if (!feedbackText.equals(other.feedbackText))
			return false;
		if (format != other.format)
			return false;
		if (interviewId != other.interviewId)
			return false;
		if (statusId != other.statusId)
			return false;
		return true;
	}
    
	
}
