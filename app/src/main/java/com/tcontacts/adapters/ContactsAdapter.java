package com.tcontacts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcontacts.ApplicationLoader;
import com.tcontacts.R;
import com.tcontacts.model.Group;
import com.tcontacts.model.Person;

import java.util.List;

public class ContactsAdapter extends BaseExpandableListAdapter {
	private List<Group> groups;
	private LayoutInflater layoutInflater;
	
	public ContactsAdapter(List<Group> groups) {
		this.groups = groups;
		this.layoutInflater = (LayoutInflater) ApplicationLoader.getApplicationInstance().getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getGroupCount() {
		return groups.size();
	}
	
	@Override
	public int getChildrenCount(int i) {
		List<Person> contacts = groups.get(i).getPeople();
		return contacts.size();
	}
	
	@Override
	public Object getGroup(int i) {
		return groups.get(i);
	}
	
	@Override
	public Object getChild(int i, int i1) {
		List<Person> contacts = groups.get(i).getPeople();
		return contacts.get(i1);
	}
	
	@Override
	public long getGroupId(int i) {
		return i;
	}
	
	@Override
	public long getChildId(int i, int i1) {
		return i1;
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	@Override
	public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
		GroupViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item_group, null);
			holder = new GroupViewHolder();
			holder.groupName = convertView.findViewById(R.id.list_item_group_name);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		
		holder.groupName.setText(ApplicationLoader.convertToNormalFormat(groups.get(i).getGroupName()));
		
		return convertView;
	}
	
	static class GroupViewHolder {
		TextView groupName;
	}
	
	@Override
	public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
		ChildViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item_contact, null);
			holder = new ChildViewHolder();
			holder.contactName = convertView.findViewById(R.id.txt_view_contact_name);
			holder.contactStatus = convertView.findViewById(R.id.txt_view_contact_status);
			holder.contactStatusIco = convertView.findViewById(R.id.img_status);
			holder.contactAvatar = convertView.findViewById(R.id.img_user_avatar);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		
		Person person = (Person) getChild(i, i1);
		holder.contactName.setText(ApplicationLoader.convertToNormalFormat(person.getFullName()));
		holder.contactStatus.setText(ApplicationLoader.convertToNormalFormat(person.getStatusMessage()));
		holder.contactStatusIco.setImageResource(getStatusResId(person.getStatusIcon()));
		
		return convertView;
	}
	
	private int getStatusResId(String status) {
		int resId = R.drawable.contacts_list_status_online;
		
		switch (status) {
			case "online":
				resId = R.drawable.contacts_list_status_online;
				break;
			case "offline":
				resId = R.drawable.contacts_list_status_offline;
				break;
			case "busy":
				resId = R.drawable.contacts_list_status_busy;
				break;
			case "away":
				resId = R.drawable.contacts_list_status_away;
				break;
			case "callforwarding":
				resId = R.drawable.contacts_list_status_pending;
				break;
		}
		
		return resId;
	}
	
	static class ChildViewHolder {
		TextView contactName;
		TextView contactStatus;
		ImageView contactStatusIco;
		ImageView contactAvatar;
	}
	
	@Override
	public boolean isChildSelectable(int i, int i1) {
		return false;
	}
}