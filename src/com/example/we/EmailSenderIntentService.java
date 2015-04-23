package com.example.we;

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

	private final static String SENDER_EMAIL_ADDRESS = "fmerzadyan@gmail.com";
	private String RECEIVER_EMAIL_ADDRESS;
	private Session session;
	private Message message;
	private boolean isSent;
	private CodeGenerator gen;
	private static final String CODE_VERIFIED_FLAG = "CODE_VERIFIED_FLAG";
	private static final String CODE_NOT_YET_VERIFIED_FLAG = "CODE_NOT_YET_VERIFIED_FLAG";
	private Bundle person;
	private Handler mainThread;
	private static final String Email_Sender_Intent_Service_TAG = "Email_Sender_Intent_Service";
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
		person = intent.getExtras();
		//checks the hashmap for the key "CODE_CONFIRMED" to see if it returns true or false
		if(person.getBoolean(CodeConfirmActivity.CODE_CONFIRMED))
		{
			this.RECEIVER_EMAIL_ADDRESS = person.getString(INTENT_INTO_SERVICE).toString();
			createSession();
			createMessage(CODE_VERIFIED_FLAG);
			sendMessage();
		}
		else
		{
			this.RECEIVER_EMAIL_ADDRESS = person.getString(INTENT_INTO_SERVICE).toString();
			createSession();
			createMessage(CODE_NOT_YET_VERIFIED_FLAG);
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
			transferIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			person.putInt("code", gen.getCode());
			transferIntent.putExtras(person);
			startActivity(transferIntent);
			Log.d(Email_Sender_Intent_Service_TAG, "onHandleIntent was called successfully");
		}
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

	private void createMessage(String command)
	{
		final String errorMsg = "ERROR in message creation";
		final String successMsg = "SUCCESS in message creation";
		if(command.equals(CODE_VERIFIED_FLAG))
		{
			try
			{
				message = new MimeMessage(session);
				message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(RECEIVER_EMAIL_ADDRESS));
				message.setSubject("WE: Adam and Eve's Wedding: Verification");
				String code = ""+ person.getInt("code");
				String forename = person.getString("forename");
				String surname = person.getString("surname");
				//int type can only store 10 digits. easy fix was to just as 0 as a prefix to complete phone number
				String phone = "0" + person.getLong("phone");
				String email = person.getString("email");
				message.setContent("<h1>Successful Verification</h1>"+"\n"+code+"\n"+forename+"\n"+surname+"\n"+phone+"\n"+email,"text/html");
				Log.d(Email_Sender_Intent_Service_TAG, successMsg);

			} catch (MessagingException e) {
				Log.e(Email_Sender_Intent_Service_TAG, errorMsg);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		else	
		{
			try
			{
				message = new MimeMessage(session);
				message.setFrom(new InternetAddress(SENDER_EMAIL_ADDRESS));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(RECEIVER_EMAIL_ADDRESS));
				message.setSubject("WE: Adam and Eve's Wedding: Verification");
				gen = new CodeGenerator();
				Log.i("createMessage", "code is: "+gen.getCode());
				message.setContent("<h1>Confirmation code:</h1>"+gen.code,"text/html");
				Log.d(Email_Sender_Intent_Service_TAG, successMsg);
			} catch (MessagingException e) {
				Log.e(Email_Sender_Intent_Service_TAG, errorMsg);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	private void sendMessage()
	{
		try {
			Transport.send(message);
			isSent=true;
			Log.d(Email_Sender_Intent_Service_TAG, "Message sent");
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
