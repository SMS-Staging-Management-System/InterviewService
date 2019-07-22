package com.revature.dtos;

import java.util.ArrayList;

public class EmailList {
	private ArrayList<String> emailList;

	
	public EmailList(ArrayList<String> emailList) {
		super();
		this.emailList = emailList;
	}

	public EmailList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getEmailList() {
		return emailList;
	}

	public void setEmailList(ArrayList<String> emailList) {
		this.emailList = emailList;
	}

	@Override
	public String toString() {
		return "EmailList [emailList=" + emailList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailList == null) ? 0 : emailList.hashCode());
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
		EmailList other = (EmailList) obj;
		if (emailList == null) {
			if (other.emailList != null)
				return false;
		} else if (!emailList.equals(other.emailList))
			return false;
		return true;
	}
	
	
}
