package com.tcontacts.model;

import java.util.List;

public class Group {
	private String groupName;
	private List<Person> people;
	
	public Group(String groupName, List<Person> people) {
		this.groupName = groupName;
		this.people = people;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public List<Person> getPeople() {
		return people;
	}
	
	@Override
	public String toString() {
		return "Group{" +
				"groupName='" + groupName + '\'' +
				", people=" + people +
				'}';
	}
}