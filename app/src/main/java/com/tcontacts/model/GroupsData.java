package com.tcontacts.model;

import java.util.List;

public class GroupsData {
	private List<Group> groups;
	
	public List<Group> getGroups() {
		return groups;
	}
	
	@Override
	public String toString() {
		return "GroupsData{" +
				"groups=" + groups +
				'}';
	}
}