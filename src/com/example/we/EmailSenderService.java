package com.example.we;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class EmailSenderService extends IntentService{
	
	
	
	
	


	public EmailSenderService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private final static String SENDER_EMAIL_ADDRESS = "fmerzadyan@gmail.com";
	private String RECEIVER_EMAIL_ADDRESS;
	private Session session;
	private Message message;
	private boolean is_message_sent;




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
			message.setContent("<h1>This is actual message embedded in HTML tags</h1>","text/html");

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
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Let it continue running until it is stopped.
		super.onStartCommand(intent, flags, startId);
		is_message_sent=false;
		RECEIVER_EMAIL_ADDRESS = intent.getStringExtra("email");
		Log.i("onStartCommand", RECEIVER_EMAIL_ADDRESS);
		createSession();
		createMessage();
		sendMessage();
		onDestroy();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(is_message_sent)
		{
			Toast.makeText(this, "Email is sent", Toast.LENGTH_LONG).show();
			is_message_sent=false;
		}

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}


}
