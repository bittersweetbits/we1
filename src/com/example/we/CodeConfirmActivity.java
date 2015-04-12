package com.example.we;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CodeConfirmActivity extends BaseActivity  implements OnClickListener {

	private EditText etxtConfirm;
	private Button btnConfirm;
	private Bundle person;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.code_confirm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			//TODO: //match therefore add this person to the database
			//from here: get all extra info from person (Bundle) and input them into a database of some sort-won't take too long
		}
		else
		{
			Log.i("confirm", "codes do not match: real"+sysCode + " input "+usrCode);
		}
	}


	private void revert()
	{
		//TODO: once finished whether incorrect or correct just revert back to the TicketActivity. from there the user can either add more 
		//people OR just return back to the Main
	}

}
