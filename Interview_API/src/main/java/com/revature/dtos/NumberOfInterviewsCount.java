package com.revature.dtos;

import java.util.Arrays;

public class NumberOfInterviewsCount {

	// total number of associates tracked
	public int totalNumber;
	public int[] countData;
	
	
	
	public NumberOfInterviewsCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NumberOfInterviewsCount(int totalNumber, int[] countData) {
		super();
		this.totalNumber = totalNumber;
		this.countData = countData;
	}

	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int[] getCountData() {
		return countData;
	}
	public void setCountData(int[] countData) {
		this.countData = countData;
	}

	@Override
	public String toString() {
		return "NumberOfInterviewsCount [totalNumber=" + totalNumber + ", countData=" + Arrays.toString(countData)
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(countData);
		result = prime * result + totalNumber;
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
		NumberOfInterviewsCount other = (NumberOfInterviewsCount) obj;
		if (!Arrays.equals(countData, other.countData))
			return false;
		if (totalNumber != other.totalNumber)
			return false;
		return true;
	}
	
	
	
}
