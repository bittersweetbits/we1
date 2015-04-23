package com.example.we;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.widget.VideoView;

public class Splash extends Activity implements Runnable, OnCompletionListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		VideoView video = (VideoView) findViewById(R.id.videoView);
		video.setVideoPath("android.resource://com.example.we/raw/" + R.raw.wedding_event);
		video.start();
		video.setOnCompletionListener(this);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
}
