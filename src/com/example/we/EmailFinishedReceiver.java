package com.example.we;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class EmailFinishedReceiver extends BroadcastReceiver {

	public static final String EMAIL_DONE = "EMAIL_DONE";
	/**
	 * this is called when a broadcast is received
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// create a handler to post messages to the main thread
	    
		Log.i("onRecieve", "EmailFinishedReceiver at least this runs");
		
	}
	
}
