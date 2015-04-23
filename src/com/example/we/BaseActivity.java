package com.example.we;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends  ActionBarActivity {
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

		case R.id.action_get_main:
			openMain();
			return true;
		case R.id.action_get_tickets:
			openTicket();
			return true;
		case R.id.action_map:
			openMap();
			return true;
		case R.id.action_faq:
			openFAQ();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openFAQ() {
		if(!(this.getClass().equals(FAQActivity.class)))
		{
			Intent i = new Intent(this, FAQActivity.class);
			this.startActivity(i);
			this.onStop();
		}
	}
	private void openMap() {

		if(!(this.getClass().equals(MapActivity.class)))
		{
			Intent i = new Intent(this,MapActivity.class );
			this.startActivity(i);
			this.onStop();
		}
	}

	private void openTicket()
	{
		if(!(this.getClass().equals(TicketActivity.class)))
		{
			Intent i = new Intent(this,TicketActivity.class );
			this.startActivity(i);
			this.onStop();
		}
	}

	private void openMain()
	{
		if(!(this.getClass().equals(MainActivity.class)))
		{
			Intent i = new Intent(this, MainActivity.class);
			this.startActivity(i);
			this.onStop();
		}
	}






}
