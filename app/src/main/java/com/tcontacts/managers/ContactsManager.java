package com.tcontacts.managers;

import android.util.Log;

import com.google.gson.Gson;
import com.tcontacts.ApplicationLoader;
import com.tcontacts.model.Group;
import com.tcontacts.model.GroupsData;
import com.tcontacts.model.Person;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsManager {
	private static final String TAG = ContactsManager.class.getSimpleName();
	
	private List<Group> groups = new ArrayList<>();
	
	public ContactsManager() {
	
	}
	
	public Observable<ArrayList<Group>> getGroups() {
		if (groups.isEmpty()) {
			return getFromAssets("contacts.json");
		} else {
			return Observable.just(new ArrayList<>(groups));
		}
	}
	
	private Observable<ArrayList<Group>> getFromAssets(String fileName) {
		return Observable.just(ApplicationLoader.getApplicationInstance().getAssetJsonData(fileName))
				.doOnNext(json -> Log.d(TAG, "Json : " + json))
				.switchMap(json -> {
					GroupsData groupsData = new Gson().fromJson(json, GroupsData.class);
					return Observable.just(new ArrayList<>(groupsData.getGroups()));
				})
				.doOnNext(groups -> this.groups.addAll(groups))
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}
	
	public Observable<ArrayList<Group>> search(String text) {
		return Observable.just(text)
				.map(String::toLowerCase)
				.switchMap(searchWord -> {
					List<Group> groupSearch = new ArrayList<>();
					
					for (int i = 0; i < groups.size(); i++) {
						Group group = groups.get(i);
						
						List<Person> searchPeople = new ArrayList<>();
						List<Person> people = group.getPeople();
						
						for (int j = 0; j < people.size(); j++) {
							if (people.get(j).getFullName().toLowerCase().contains(searchWord)) {
								searchPeople.add(people.get(j));
							}
						}
						
						if (searchPeople.size() != 0) {
							groupSearch.add(new Group(group.getGroupName(), searchPeople));
						}
					}
					
					return Observable.just(new ArrayList<>(groupSearch));
				});
	}
}