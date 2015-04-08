package com.example.we;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends BaseActivity implements OnClickListener{
	private TextView venueName;
	private TextView address;
	private final String churchSite = "http://huddersfieldparishchurch.org";
	private final String churchMap = "https://www.google.com/maps/place/Huddersfield+St+Peters+C+of+E+Parish+Church/@53.64827,-1.781922,16z/data=!4m2!3m1!1s0x0:0x313b2cbd27fe4eee?hl=en";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		venueName = (TextView) findViewById(R.id.venu_name_textview);
		address = (TextView) findViewById(R.id.address_textview);
		venueName.setOnClickListener(this);
		address.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.venu_name_textview:
			//open church site
			openSite(churchSite);
			break;
		case R.id.address_textview:
			//open google maps maybe?
			openSite(churchMap);
			break;
		}
	}
	
	public void openSite(String uriString)
	{
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
		startActivity(i);
		this.onStop();
	}






}
