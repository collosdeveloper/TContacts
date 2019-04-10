package com.tcontacts.model;

import android.text.TextUtils;

public class Person {
	private String firstName;
	private String lastName;
	private String statusIcon;
	private String statusMessage;
	
	public String getFirstName() {
		return TextUtils.isEmpty(firstName) ? "" : firstName;
	}
	
	public String getLastName() {
		return TextUtils.isEmpty(lastName) ? "" : lastName;
	}
	
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	
	public String getStatusIcon() {
		return statusIcon;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
	
	@Override
	public String toString() {
		return "Person{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", statusIcon='" + statusIcon + '\'' +
				", statusMessage='" + statusMessage + '\'' +
				'}';
	}
}