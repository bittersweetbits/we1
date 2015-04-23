package com.example.we;


import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;



@SuppressWarnings("deprecation")
public class MapActivity extends FragmentActivity implements OnClickListener{
	private static final LatLng CHURCH = new LatLng(53.647291,-1.781422);
	private GoogleMap map;
	private Button btnDirections;
	private EditText etxtStartLocation;
	private MarkerOptions mrkoptChurch;
	private MarkerOptions mrkoptStart;
	private Polyline line;
	private Marker mrkStart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
		}

		etxtStartLocation = (EditText) findViewById(R.id.start_location);
		btnDirections = (Button) findViewById(R.id.load_direction_button);
		btnDirections.setOnClickListener(this);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mrkoptChurch = new MarkerOptions().position(CHURCH).title("Adam & Eve's Wedding!").draggable(false);
		map.addMarker(mrkoptChurch);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(CHURCH, 21));
		map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.load_direction_button:
			if(line!=null)
			{
				line.remove();
				mrkStart.remove();
			}
			String strStart = etxtStartLocation.getText().toString();
			LatLng latlngStart = getGeoCode(URLEncoder.encode(strStart));
			mrkoptStart = new MarkerOptions().position(latlngStart).title("Starting location");
			mrkStart = map.addMarker(mrkoptStart);
			line = map.addPolyline(new PolylineOptions().geodesic(true).add(latlngStart).add(CHURCH));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngStart,10));
			break;
		}
	}

	private LatLng getGeoCode(String address) {
		String uri = "http://maps.google.com/maps/api/geocode/json?address="+address+"&sensor=false";
		HttpGet httpGet = new HttpGet(uri);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;
		StringBuilder s = new StringBuilder();
		//set coordinates to (0,0) if try statement fails
		LatLng latlng = new LatLng(0,0);
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream i = httpEntity.getContent();
			int b;
			//while there is content to change then execute
			while ((b = i.read())!=-1) {
				s.append((char) b);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject j = new JSONObject();
		//get LatLng coords into LatLng object
		try {
			j = new JSONObject(s.toString());
			double lat = ((JSONArray)j.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			double lng = ((JSONArray)j.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			latlng = new LatLng(lat,lng);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return latlng;
	}
























}