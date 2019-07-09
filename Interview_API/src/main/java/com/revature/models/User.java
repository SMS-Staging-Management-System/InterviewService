package com.revature.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revature.models.Address;
import com.revature.models.Cohort;
import com.revature.models.Status;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "sms_users", schema = "user_service")
public class User {

	@Id
	@Column(name = "sms_user_id", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String email;

	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "training_address")
	private Address trainingAddress;

	@ManyToOne
	@JoinColumn(name = "personal_address")
	private Address personalAddress;

	@ManyToOne
	@JoinColumn(name = "user_status")
	private Status userStatus;


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usersCohorts", joinColumns = { @JoinColumn(name = "smsUserId") }, inverseJoinColumns = {
	@JoinColumn(name = "cohortId") })
	@JsonIgnore
	private Set<Cohort> cohorts = new HashSet<>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getTrainingAddress() {
		return trainingAddress;
	}

	public void setTrainingAddress(Address trainingAddress) {
		this.trainingAddress = trainingAddress;
	}

	public Address getPersonalAddress() {
		return personalAddress;
	}

	public void setPersonalAddress(Address personalAddress) {
		this.personalAddress = personalAddress;
	}

	public Status getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Status userStatus) {
		this.userStatus = userStatus;
	}

	public Set<Cohort> getCohorts() {
		return cohorts;
	}

	public void setCohorts(Set<Cohort> cohorts) {
		this.cohorts = cohorts;
	}

	public User(int userId, @NotNull String firstName, @NotNull String lastName, @NotNull String email,
			String phoneNumber, Address trainingAddress, Address personalAddress, Status userStatus,
			Set<Cohort> cohorts) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.trainingAddress = trainingAddress;
		this.personalAddress = personalAddress;
		this.userStatus = userStatus;
		this.cohorts = cohorts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cohorts == null) ? 0 : cohorts.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((personalAddress == null) ? 0 : personalAddress.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((trainingAddress == null) ? 0 : trainingAddress.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
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
		User other = (User) obj;
		if (cohorts == null) {
			if (other.cohorts != null)
				return false;
		} else if (!cohorts.equals(other.cohorts))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (personalAddress == null) {
			if (other.personalAddress != null)
				return false;
		} else if (!personalAddress.equals(other.personalAddress))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (trainingAddress == null) {
			if (other.trainingAddress != null)
				return false;
		} else if (!trainingAddress.equals(other.trainingAddress))
			return false;
		if (userId != other.userId)
			return false;
		if (userStatus == null) {
			if (other.userStatus != null)
				return false;
		} else if (!userStatus.equals(other.userStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", trainingAddress=" + trainingAddress + ", personalAddress="
				+ personalAddress + ", userStatus=" + userStatus + ", cohorts=" + cohorts + "]";
	}
}