package com.dhanabal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendRegistrationMail(String toEmail, String userName, String mobile, String password) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("vdhanabal2003@gmail.com");
		message.setTo(toEmail);
		message.setSubject("Welcome to D'Cart");

		message.setText("Hello " + userName + ",\n\n" + 
						"Your D'Cart account has been created successfully.\n\n"+ 
						"Now you can login and start shopping.\n\n" + 
						"\n\n" + 
						"Login Credentials:\n\n" + 
						"Registered email: "+ toEmail + "\n\n" + 
						"Registered mobile: "+mobile+"\n\n"+
						"Password: " + password + "\n\n" +
						"\n\n" + 
						"Thank you,\n" + 
						"Team D'Cart");

		mailSender.send(message);

	}

}