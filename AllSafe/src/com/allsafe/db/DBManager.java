package com.allsafe.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public abstract class DBManager extends SQLiteOpenHelper{
	
	private static final String DB_FILE_NAME = "contacts.db";
	private String tableName;
	private String[] columns;
	
	public DBManager(Context context,String tableName, String [] columns) {
		super(context, DB_FILE_NAME, null, 1);
		this.tableName = tableName;
		this.columns = columns;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + tableName + 
		" (" + BaseColumns._ID + " integer primary key autoincrement "; 
		
		for (int i=0;i<columns.length; i++) {
			sql+=", "+columns[i];
		}
		
		sql+=" ) ";
		
		db.execSQL( sql );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void insert(ContentValues values){
		getWritableDatabase().insert(tableName, null, values);
	}
	
	public Cursor getAll(){
		return getReadableDatabase().query(tableName, null, null, null, null, null, null);
	}
	
	

}
