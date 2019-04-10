package com.tcontacts;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tcontacts.managers.ContactsManager;

import java.io.IOException;
import java.io.InputStream;

public class ApplicationLoader extends MultiDexApplication {
	private static final String TAG = ApplicationLoader.class.getSimpleName();
	
	private static ApplicationLoader applicationInstance;
	
	private ContactsManager contactsManager;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		applicationInstance = this;
	}
	
	public static ApplicationLoader getApplicationInstance() {
		return applicationInstance;
	}
	
	public ContactsManager getContactsManager() {
		ContactsManager localInstance = contactsManager;
		if (localInstance == null) {
			synchronized (ContactsManager.class) {
				localInstance = contactsManager;
				if (localInstance == null) {
					contactsManager = localInstance = new ContactsManager();
				}
			}
		}
		
		return localInstance;
	}
	
	public String getAssetJsonData(String fileName) {
		String json;
		try {
			InputStream is = getAssets().open(fileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
		Log.d(TAG, "Contacts json : " + json);
		
		return json;
	}
	
	public static String convertToNormalFormat(String str) {
		char ch[] = str.toCharArray();
		for (int i = 0; i < str.length(); i++) {
			if (i == 0 && ch[i] != ' ' ||
					ch[i] != ' ' && ch[i - 1] == ' ') {
				// If it is in lower-case
				if (ch[i] >= 'a' && ch[i] <= 'z') {
					// Convert into Upper-case
					ch[i] = (char) (ch[i] - 'a' + 'A');
				}
			}
			
			// If apart from first character
			// Any one is in Upper-case
			else if (ch[i] >= 'A' && ch[i] <= 'Z')
				// Convert into Lower-Case
				ch[i] = (char) (ch[i] + 'a' - 'A');
		}
		
		// Convert the char array to equivalent String
		String st = new String(ch);
		return st;
	}
}