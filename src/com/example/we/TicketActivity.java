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



public class TicketActivity extends BaseActivity implements OnClickListener{

	private EditText forename;
	private EditText surname;
	private EditText phone;
	private EditText email;
	private Button requestTicket;
	private BroadcastReceiver Email_Finished_Reciever;
	private EditText etxtConfirm;
	private Button btnConfirm;
	private Bundle b;

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
		this.etxtConfirm = (EditText) findViewById(R.id.confirm_code_edittext);
		this.btnConfirm = (Button) findViewById(R.id.confirm_code_button);
		this.btnConfirm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.ticket_request_button:
			submitRequest();
			break;
		case R.id.confirm_code_button:
			confirm();
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
			person.putLong("phone", Long.parseLong(phone));
			person.putString("email", email);
			
			//use INTENT_INTO_SERVICE string (acts as key) and maps the user's email address (acts as value)
			person.putString(EmailSenderIntentService.INTENT_INTO_SERVICE, email);
			//i.putExtra(EmailSenderIntentService.INTENT_INTO_SERVICE, email);
			//putting all the required info into a bundle and the bundle into the intent
			i.putExtras(person);
			startService(i);
			Log.i("startEmailService", "email service was called");
			
		}
		else
		{
			Log.i("TICKETACTIVITY", "Validations failed");
		}
	}

	
	
	

	@Override
	public void onResume()
	{
		super.onResume();
		IntentFilter iF = new IntentFilter(EmailFinishedReceiver.EMAIL_DONE);
		Email_Finished_Reciever = new EmailFinishedReceiver();
		LocalBroadcastManager m = LocalBroadcastManager.getInstance(this);
		m.registerReceiver(Email_Finished_Reciever, iF);
		Log.i("onResume", "onResume is running");
	}
	
	/**
	 * prevents memory leaks form ongoing broadcast receiver object
	 */
	@Override 
	public void onPause()
	{
		LocalBroadcastManager m = LocalBroadcastManager.getInstance(this);
		m.unregisterReceiver(Email_Finished_Reciever);
		Log.i("onPause", "onPause is running");
		super.onPause();
	}

	private void confirm() {
		
		b = this.getIntent().getExtras();
		int realCode = b.getInt("code");
		int inCode = Integer.parseInt(etxtConfirm.getText().toString());
		if(realCode == inCode)
		{
			//match therefore add this person to the database
			
		}
		else
		{
			Log.i("confirm", "codes do not match: real"+realCode + " input "+inCode);
		}
	}

	

	


}
