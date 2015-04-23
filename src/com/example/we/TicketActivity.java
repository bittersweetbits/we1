package com.example.we;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class TicketActivity extends BaseActivity implements OnClickListener{

	private EditText forename;
	private EditText surname;
	private EditText phone;
	private EditText email;
	private Button requestTicket;
	private BroadcastReceiver Email_Finished_Reciever;
	@SuppressWarnings("unused")
	private static final String TICKET_ACTIVITY_TAG = "TICKET_ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);

		this.forename = (EditText) findViewById(R.id.forename_edittext);
		this.surname = (EditText) findViewById(R.id.surname_edittext);
		this.phone= (EditText) findViewById(R.id.phone_edittext);
		this.email= (EditText) findViewById(R.id.email_edittext);
		this.requestTicket = (Button) findViewById(R.id.ticket_request_button);
		this.requestTicket.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.ticket_request_button:
			submitRequest();
			break;
		}
	}

	private void submitRequest() 
	{
		final String forename = this.forename.getText().toString();
		final String surname = this.surname.getText().toString();
		final String phone = this.phone.getText().toString();
		final String email = this.email.getText().toString();
		//All validation checks must return true
		if(Validator.validateLength(forename)&&Validator.validateLength(surname)&&Validator.validatePhone(phone)&&Validator.validateAddress(email))
		{
			Intent i = new Intent(this, EmailSenderIntentService.class);
			Bundle person = new Bundle();
			person.putString("forename", forename);
			person.putString("surname", surname);
			//process phone into a long type
			person.putLong("phone", Long.parseLong(phone));
			person.putString("email", email);
			//use INTENT_INTO_SERVICE string (acts as key) and maps the user's email address (acts as value)
			person.putString(EmailSenderIntentService.INTENT_INTO_SERVICE, email);
			//i.putExtra(EmailSenderIntentService.INTENT_INTO_SERVICE, email);
			//putting all the required info into a bundle and the bundle into the intent
			i.putExtras(person);
			startService(i);
		}
		else
		{
			Log.e("TICKET_ACTIVITY_TAG", "Validations failed");
			Toast.makeText(getBaseContext(), "Error: retry entering details", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		IntentFilter i = new IntentFilter(EmailFinishedReceiver.EMAIL_DONE);
		Email_Finished_Reciever = new EmailFinishedReceiver();
		LocalBroadcastManager m = LocalBroadcastManager.getInstance(this);
		m.registerReceiver(Email_Finished_Reciever, i);
	}

	/**
	 * prevents memory leaks form ongoing broadcast receiver object
	 */
	@Override 
	public void onPause()
	{
		LocalBroadcastManager m = LocalBroadcastManager.getInstance(this);
		m.unregisterReceiver(Email_Finished_Reciever);
		final String msg = "onPause() is executed";
		Log.i(TICKET_ACTIVITY_TAG,msg);
		super.onPause();
	}
}
