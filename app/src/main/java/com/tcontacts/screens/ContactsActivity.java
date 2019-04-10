package com.tcontacts.screens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.tcontacts.R;
import com.tcontacts.adapters.ContactsAdapter;
import com.tcontacts.base.mvp.LifecyclePresenter;
import com.tcontacts.base.ui.MVPActivity;
import com.tcontacts.model.Group;

import java.util.ArrayList;

public class ContactsActivity extends MVPActivity implements ContactsActivityView {
	private ContactsActivityPresenter presenter = new ContactsActivityPresenter();
	
	private ExpandableListView expViewContacts;
	private TextView txtViewNoSearchResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_contacts);
		
		initUI();
	}
	
	private void initUI() {
		expViewContacts = findViewById(R.id.exp_view_contacts);
		txtViewNoSearchResult = findViewById(R.id.txt_view_empty_search);
		
		EditText edtSearch = findViewById(R.id.edt_search);
		edtSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
			
			@Override
			public void afterTextChanged(Editable editable) {
				presenter.makeSearch(editable);
			}
		});
	}
	
	@Override
	public void setGroups(ArrayList<Group> groups) {
		txtViewNoSearchResult.setVisibility(groups.isEmpty() ? View.VISIBLE : View.GONE);
		
		ContactsAdapter contactsAdapter = new ContactsAdapter(groups);
		expViewContacts.setAdapter(contactsAdapter);
		
		for (int i = 0; i < contactsAdapter.getGroupCount(); i++) {
			expViewContacts.expandGroup(i);
		}
	}
	
	@Override
	public LifecyclePresenter getPresenter() {
		return presenter;
	}
}