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
		case R.id.action_map:
			//openMap should display the map activity
			openMap();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 	The user opens the Recent Apps window and switches from your app to another app. The activity in your app that's currently in the foreground is stopped. If the user returns to your app from the Home screen launcher icon or the Recent Apps window, the activity restarts.
	 *	The user performs an action in your app that starts a new activity. The current activity is stopped when the second activity is created. If the user then presses the Back button, the first activity is restarted.
	 *	The user receives a phone call while using your app on his or her phone.
	 */
	private void openMap()
	{
		Log.i(TAG, "openMap");
		Intent i = new  Intent(this, Map.class);
		startActivity(i);
		onStop();
	}
	private void openSettings() {
		//Create settings options:: see sub-menus
		Log.i(TAG, "openSettings");
	}
}
