package com.example.we;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_main, menu);
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

	private void openSettings() {
		// TODO Auto-generated method stub
		
	}
	
	private void openMap()
	{
		Runnable runner = new Runnable() {public void run() {
			Intent intent = new Intent( MainActivity.this, Map.class );
			MainActivity.this.startActivity( intent );
			MainActivity.this.finish();
			}};
			Handler handler = new Handler();
			handler.post(runner);	
	}
	
	
}
