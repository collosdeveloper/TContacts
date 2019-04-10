package com.tcontacts.screens;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;

import com.tcontacts.ApplicationLoader;
import com.tcontacts.base.mvp.LifecyclePresenter;
import com.tcontacts.base.mvp.State;
import com.tcontacts.utils.RxUtils;

public class ContactsActivityPresenter extends LifecyclePresenter<ContactsActivityView> {
	
	@Override
	public void onStart() {
		super.onStart();
		
		setGroups();
	}
	
	private void setGroups() {
		monitor(ApplicationLoader.getApplicationInstance().getContactsManager().getGroups()
				.subscribe(groups -> {
					Log.d(TAG, "Groups : " + groups.toString());
					getView().setGroups(groups);
				}, RxUtils.getEmptyErrorConsumer(TAG, "setGroups")), State.STOP);
		
	}
	
	public void makeSearch(Editable editable) {
		if(TextUtils.isEmpty(editable)) {
			setGroups();
		} else {
			monitor(ApplicationLoader.getApplicationInstance().getContactsManager().search(editable.toString())
					.subscribe(groups -> {
						Log.d(TAG, "Search result : " + groups.toString());
						getView().setGroups(groups);
					}, RxUtils.getEmptyErrorConsumer(TAG, "makeSearch")), State.STOP);
		}
	}
}