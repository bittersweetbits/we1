package com.example.we;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends  ActionBarActivity {
	private final String TAG = "BASEACTIVITY";
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {

		case R.id.action_settings:
			//openSettings should display the settings options
			openSettings();
			return true;
		case R.id.action_get_tickets:
			//open form to get tickets
			Log.i("OnOptionsItemSelected is called", "get ticket button is pressed");
			openTicket();
			Log.i("OnOptionsItemSelected-->action_get_tickets", "openTicket() was called");
			return true;
		case R.id.action_map:
			//open map activity
			openMap();
			Log.i("OnOptionsItemsSelected","openMap()");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	private void openMap() {
		Intent i = new Intent(this,MapActivity.class );
		this.startActivity(i);
		this.finish();
	}
	private void openSettings() 
	{
		//Create settings options:: see sub-menus
		Log.i(TAG, "openSettings");
	}
	
	private void openTicket()
	{
		Intent i = new Intent(this,TicketActivity.class );
		this.startActivity(i);
		this.finish();
	}
	
	
	
	
	
	
	
}
