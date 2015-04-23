package com.example.we;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends BaseActivity implements OnClickListener{
	private TextView venueName;
	private TextView address;
	private final String churchSite = "http://huddersfieldparishchurch.org";
	private final String churchMap = "https://www.google.com/maps/place/Huddersfield+St+Peters+C+of+E+Parish+Church/@53.64827,-1.781922,16z/data=!4m2!3m1!1s0x0:0x313b2cbd27fe4eee?hl=en";
	private Button shareButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		venueName = (TextView) findViewById(R.id.venu_name_textview);
		address = (TextView) findViewById(R.id.address_textview);
		venueName.setOnClickListener(this);
		address.setOnClickListener(this);
		shareButton = (Button) findViewById(R.id.share_button);
		shareButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.venu_name_textview:
			openLink(churchSite);
			break;
		case R.id.address_textview:
			openLink(churchMap);
			break;
		case R.id.share_button:
			share();
			break;
		}
	}

	private void share() {
		Intent i = new Intent(android.content.Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(android.content.Intent.EXTRA_SUBJECT,getString(R.string.title_activity_main));
		String body = getString(R.string.wedding_brief) +"\n"+  "@"+ getString(R.string.date) +"\n"+  "@"+ getString(R.string.venue_name) +"\n"+  "@"+ getString(R.string.address);
		i.putExtra(android.content.Intent.EXTRA_TEXT, body);
		startActivity(Intent.createChooser(i, "Share via"));
	}

	public void openLink(String uriString)
	{
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
		startActivity(i);
		this.onStop();
	}


}
