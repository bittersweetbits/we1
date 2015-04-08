package com.example.we;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class EmailSenderIntentService extends IntentService {
	public Handler mainThread;
	public static final String INTENT_INTO_SERVICE = "INTENT_STARTED";
	
	
	@Override
	public void onCreate() {
	    super.onCreate();
	    mainThread = new Handler();
	}
	
	public EmailSenderIntentService() {
		super("EmailSenderIntentService");
		Log.i("onHandleIntent", "EmailSenderIntent contructor was called");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//get the data value mapped with this key (INTENT_INTO_SERVICE is the key)
		//and value (should be user's email address) is passed into "input" String
		final String input = intent.getStringExtra(INTENT_INTO_SERVICE);
		
		//when the work is done
		Intent i = new Intent();
		i.setAction(EmailFinishedReceiver.EMAIL_DONE);
		LocalBroadcastManager m = LocalBroadcastManager.getInstance(this);
		m.sendBroadcastSync(i);
		/*
		 * use mainThread to post this Toast on TicketActivity activity (this IntentService runs on a worker (different) thread)
		 */
		 mainThread.post(new Runnable() {            
		        @Override
		        public void run() {
		            Toast.makeText(EmailSenderIntentService.this, "Confirmation email is sent to: "+input.toString()+" please check your email.", Toast.LENGTH_LONG).show();                
		        }
		    });
		Log.i("onHandleIntent", "onHandleIntent was called");
	}
	
	

}
