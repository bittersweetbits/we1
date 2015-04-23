package com.example.we;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CodeConfirmActivity extends BaseActivity  implements OnClickListener {

	private EditText etxtConfirm;
	private Button btnConfirm;
	private Bundle person;
	public static final String CODE_CONFIRMED = "CODE_CONFIRMED";
	private static final String CODE_CONFIRMED_ACTIVITY_TAG = "CODE_CONFIRMED_ACTIVITY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code_confirm);
		etxtConfirm = (EditText) findViewById(R.id.confirm_code_edittext);
		btnConfirm = (Button) findViewById(R.id.confirm_code_button);
		btnConfirm.setOnClickListener(this);
		person = this.getIntent().getExtras();
	}


	

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.confirm_code_button:
			confirm();
			break;
		}
	}

	private void confirm() {
		int sysCode = person.getInt("code");
		int usrCode = Integer.parseInt(etxtConfirm.getText().toString());
		if(sysCode == usrCode)
		{
			//code =  ""+ person.getInt("code");
			//forename = person.getString("forename");
			//surname = person.getString("surname");
			//int type can only store 10 digits. easy fix was to just as 0 as a prefix to complete phone number
			//phone = "0" + person.getLong("phone");
			//email = person.getString("email");
			Intent i = new Intent(this, EmailSenderIntentService.class);
			//enter organiser's email address
			person.putString(EmailSenderIntentService.INTENT_INTO_SERVICE, "blackolive333@gmail.com");
			person.putBoolean(CODE_CONFIRMED, true);
			i.putExtras(person);
			startService(i);
			Log.i(CODE_CONFIRMED_ACTIVITY_TAG, CODE_CONFIRMED);
			close();
		}
		else
		{
			final String msg = "ERROR: Verification codes did not match"+ " system gen code: " + sysCode + " user's input code: "+usrCode;
			Log.e(CODE_CONFIRMED_ACTIVITY_TAG,msg);
			Toast.makeText(getBaseContext(), "Wrong code entered", Toast.LENGTH_LONG).show();
		}

	}
	
	private void close()
	{
		Intent i = new Intent(this, TicketActivity.class);
		startActivity(i);
		finish();
	}
}
