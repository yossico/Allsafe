package com.example.testapp;

import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	static String senderID = "72973993133";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, senderID);
			// Log.v("GCM - test", "Already registered");
			// gcm_status.setText(R.string.GCM_Registering);
		} else {
			Log.v("GCM - test", "Already registered");
			// gcm_status.setText(R.string.GCM_Ready);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
