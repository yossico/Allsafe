package com.allsafe.gcm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.allsafe.MainActivity;
import com.allsafe.R;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	static String senderID = "72973993133";

	public GCMIntentService() {
		super(senderID);
		// SENDER_ID is my project id into google account url
		// TODO Auto-generated constructor stub
		Log.d(TAG, "[GCMIntentService] start");

	}

	public GCMIntentService(String senderId) {
		super(senderId);
		// TODO Auto-generated constructor stub
		Log.d(TAG, "[GCMIntentService] start - sender Id : " + senderId);

	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {
		// TODO Auto-generated method stub

		Bundle extras = intent.getExtras();
		String message = "";

		if (extras != null) {

			message = extras.getString("message");
		}

		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = getString(R.string.app_name);
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);

		Context context = getApplicationContext();
		CharSequence contentTitle = getString(R.string.app_name);
		CharSequence contentText = message;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;

		PendingIntent contentIntent;

		Intent i = new Intent(context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		contentIntent = PendingIntent.getActivity(ctx, 0, i, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);

		mNotificationManager.notify(1, notification);

		final Bundle bundle = intent.getExtras();

	}

	@Override
	protected void onRegistered(Context ctx, String regId) {
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String myPhoneNumber = telephonyManager.getLine1Number();
		String httpReq = "http://allsafeisrael.appspot.com/api?register=" + regId + "," + myPhoneNumber;

		try {
			HttpClient client = new DefaultHttpClient();

			HttpGet request = new HttpGet();

			request.setURI(new URI(httpReq));

			HttpResponse response = client.execute(request);

			BufferedReader inBuff = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder lines = new StringBuilder();
			String line;

			while ((line = inBuff.readLine()) != null) {

				lines.append(line);

			}

			inBuff.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onUnregistered(Context ctx, String arg1) {
		// TODO Auto-generated method stub

	}

	/*
	 * private void sendToServer(Context ctx,String regId){ HttpConnect hc1 =
	 * new HttpConnect(ctx, HttpConnect.GCM_TEST_SERVER); hc1.execute(regId); }
	 */

	@Override
	protected void onError(Context ctx, String arg1) {
		// TODO Auto-generated method stub

	}

}