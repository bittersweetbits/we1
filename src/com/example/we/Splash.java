package com.example.we;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class Splash extends Activity implements Runnable, OnCompletionListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		/*
		 * use Handler object to craete a delay via threading
		 * Handler splashHandler = new Handler();splashHandler.postDelayed(this, 4000);
		 */
		
		VideoView video = (VideoView) findViewById(R.id.videoView);
		video.setVideoPath("android.resource://com.example.we/raw/" + R.raw.wedding_event);
		video.start();
		video.setOnCompletionListener(this);
	}
	
	

	/**
	 * this starts the fires off the next activity (GUI)
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
	
	/**
	 * once video is finished, this starts the fires off the next activity (GUI)
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
}
