package com.allsafe;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Contact> implements
		ListAdapter {

	public ContactsAdapter(Context context, List<Contact> contacts) {
		super(context,android.R.id.text1, contacts );
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		 View row = convertView;
		 Contact contact = getItem(position);
		 
		 ContactHolder holder;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(R.layout.contact_list_item, parent, false);
            holder = new ContactHolder();
            holder.image = (ImageView)row.findViewById(R.id.image);
            holder.name = (TextView)row.findViewById(R.id.text);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ContactHolder)row.getTag();
        }
        
        holder.name.setText(contact.getName()+"-"+contact.getPhone());
        holder.image.setImageResource(getStatusImage(contact));
        
       return row;
    }
	

	private int getStatusImage(Contact contact) {
		if(contact.getStatus()==Status.NONE){
			return R.drawable.status_no;
		}
		else if(contact.getStatus()==Status.OK){
			return R.drawable.status_ok;
		}
		else{
			return R.drawable.status_waiting;
		}
	}


	class ContactHolder{
		public ImageView image;
		public TextView name;
	}
}
