package com.allsafe;

import android.net.Uri;

public class Contact {
	private String name; 
	private String phone;
	private Status status; 
	private Uri uri;
	public Contact(String name, String phone, Uri uri) {
		this.name = name;
		this.uri = uri;
		this.phone = phone;
		status = Status.NONE;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Uri getUri() {
		return uri;
	}
	public void setUri(Uri uri) {
		this.uri = uri;
	}
	
	@Override
	public boolean equals(Object o) {
		Contact c = (Contact)o;
		return c!= null && name.equals(c.name) && phone.equals(c.phone);
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	

}
