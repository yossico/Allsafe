package com.allsafe.db;

import android.content.ContentValues;
import android.content.Context;

public class ContactsDBManager extends DBManager {

	private static final String TABLE_NAME = "CONTACTS";
	private static final String [] COLUMNS = {"NAME  text not null","PHONE  text"};
	
	public ContactsDBManager(Context context) {
		super(context, TABLE_NAME, COLUMNS);
	}
	
	public void insert(String name, String value) {
		ContentValues values = new ContentValues();
		values.put("NAME", name);
		values.put("PHONE", value);
		super.insert(values);
	}
	
	

}
