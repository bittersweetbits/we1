package com.example.we;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class EmailSenderIntentService extends IntentService {
	public static final String INTENT_INTO_SERVICE = "INTENT_STARTED";
	private Handler mainThread;
	private final static String SENDER_EMAIL_ADDRESS = "fmerzadyan@gmail.com";
	private String RECEIVER_EMAIL_ADDRESS;
	private Session session;
	private Message message;
	private boolean is_message_sent;
	private CodeGenerator gen;

	

	@Override
	public void onCreate() {
		super.onCreate();
		mainThread = new Handler();
	}

	public EmailSenderIntentService() {
		super("EmailSenderIntentService");
		Log.i("onHandleIntent", "EmailSenderIntent contructor was called");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//get the data value mapped with this key (INTENT_INTO_SERVICE is the key)
		//and value (should be user's email address) is passed into "input" String
		//final String input = intent.getStringExtra(INTENT_INTO_SERVICE);
		//final String input = intent.getExtras().getString(INTENT_INTO_SERVICE);
		Bundle person = intent.getExtras();
		//this.RECEIVER_EMAIL_ADDRESS = intent.getExtras().getString(INTENT_INTO_SERVICE).toString();
		this.RECEIVER_EMAIL_ADDRESS = person.getString(INTENT_INTO_SERVICE).toString();
		createSession();
		createMessage();
		sendMessage();

		//when the work is done: these 4 lines are to activate the broadcast receiver 
		Intent i = new Intent();
		i.setAction(EmailFinishedReceiver.EMAIL_DONE);
		LocalBroadcastManager m = LocalBroadcastManager.getInstance(this);
		m.sendBroadcastSync(i);
		
		
		 // use mainThread to post this Toast on TicketActivity activity (this IntentService runs on a worker (different) thread)
		mainThread.post(new Runnable() {            
			@Override
			public void run() {
				Toast.makeText(EmailSenderIntentService.this, "Confirmation email is sent to: "+RECEIVER_EMAIL_ADDRESS+" please check your email.", Toast.LENGTH_LONG).show();   
			}
		});
		
		//once complete: same code sent via email needs to be sent to an activity for matching against
		Intent transferIntent = new Intent(this, CodeConfirmActivity.class);
		person.putInt("code", gen.getCode());
		transferIntent.putExtras(person);
		startActivity(transferIntent);
		Log.i("onHandleIntent", "onHandleIntent was called");
	}





	private void createSession()
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		// Get the Session object.
		session = Session.getInstance(props,new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(SENDER_EMAIL_ADDRESS, "weddingevent");
			}
		});
		Log.i("createSession", "sessions was created");

	}

	private void createMessage()
	{
		try
		{
			// Create a default MimeMessage object.
			message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(RECEIVER_EMAIL_ADDRESS));
			// Set Subject: header field
			message.setSubject("Testing Subject");
			// Send the actual HTML message, as big as you like
			gen = new CodeGenerator();
			Log.i("createMessage", "code is: "+gen.getCode());
			message.setContent("<h1>Confirmation code:</h1>"+gen.code,"text/html");
					Log.i("createMessage", "message was created");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private void sendMessage()
	{
		try {
			// Send message
			Transport.send(message);
			//set a toast or something
			is_message_sent=true;
			Log.i("sendMessage", "message was sent");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public class CodeGenerator
	{
		private Random r;
		private static final int UPPER_LIMIT=9999999;
		private int code;
		public CodeGenerator()
		{
			r = new Random();
			createRandom();
		}
		private void createRandom()
		{
			int i = r.nextInt(UPPER_LIMIT);
			Log.i("createRandom()","this is the random number made: "+i);
			setCode(i);
		}
		public int getCode() {
			return code;
		}
		private void setCode(int codeMade) {
			this.code = codeMade;
		}

	}






}
